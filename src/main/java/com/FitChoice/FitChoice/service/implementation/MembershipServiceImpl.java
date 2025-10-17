package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import com.FitChoice.FitChoice.repository.*;
import com.FitChoice.FitChoice.service.interfaceses.MembershipService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    ClientRepository clientRepo;

    @Autowired
    TrainerRepository trainerRepo;

    @Autowired
    NutritionistRepository nutritionistRepo;

    @Autowired
    FitnessClassRepository fitnessClassRepo;

    @Autowired
    MembershipRepository membershipRepo;

    @Autowired
    PaymentRepository paymentRepo;

    @Override
    public MembershipDto createMembership(MembershipDto dto) {
        Membership membership = new Membership();

        Client client = clientRepo.findByUserNameIgnoreCase(dto.getClientUserName())
                .orElseThrow(() -> new RuntimeException("Client not found."));
        membership.setClient(client);

        membership.setType(dto.getType() != null ? dto.getType() : MembershipType.FULLFITNESS);
        membership.setPrice(dto.getPrice() != null ? dto.getPrice() : 150);
        membership.setStartDate(LocalDateTime.now());
        membership.setEndDate(LocalDateTime.now().plusDays(30));
        membership.setStatus(MembershipStatus.INACTIVE);

        switch (membership.getType()){
            case GYM_PRO :
                Trainer trainer = trainerRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                membership.setTrainer(trainer);
                break;

            case GYM_STAR:
                Trainer trainer1 = trainerRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                Nutritionist nutritionist = nutritionistRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No nutritionists available"));
                membership.setTrainer(trainer1);
                membership.setNutritionist(nutritionist);
                break;

            case FULLFITNESS:
                if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()){
                    Trainer chosenTrainer = trainerRepo.findTrainerByNameIgnoreCase(dto.getTrainerName())
                            .orElseThrow(() -> new RuntimeException("Trainer not found"));
                    membership.setTrainer(chosenTrainer);
                }
                if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
                    Nutritionist chosenNutritionist = nutritionistRepo.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                            .orElseThrow(() -> new RuntimeException("Nutritionist not found"));
                    membership.setNutritionist(chosenNutritionist);
                }
                break;
        }
        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .map(name -> fitnessClassRepo.findFitnessClassByNameIgnoreCase(name)
                            .orElseThrow(() -> new RuntimeException("Class not found " + name)))
                    .collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }

        double finalPrice = calculateFinalPrice(membership);
        membership.setPrice(finalPrice);

        Payment payment = new Payment();
        payment.setClient(client);
        payment.setMembership(membership);
        payment.setAmount(finalPrice);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());
        membership.setPayment(payment);

        Membership savedMembership = membershipRepo.save(membership);

        return toDto(savedMembership);
    }


    @Override
    public MembershipDto renewMembership(Long membershipId) {
       Membership oldMembership = membershipRepo.findById(membershipId)
               .orElseThrow(() -> new RuntimeException("Membership not found"));

       Membership newMembership = new Membership();
       newMembership.setClient(oldMembership.getClient());
       newMembership.setTrainer(oldMembership.getTrainer());
       newMembership.setNutritionist(oldMembership.getNutritionist());
       newMembership.setFitnessClasses(oldMembership.getFitnessClasses());
       newMembership.setType(oldMembership.getType());
       newMembership.setPrice(oldMembership.getPrice());
       newMembership.setStartDate(LocalDateTime.now());
       newMembership.setEndDate(LocalDateTime.now().plusDays(30));
       newMembership.setStatus(MembershipStatus.INACTIVE);

       double finalPrice = calculateFinalPrice(newMembership);
       newMembership.setPrice(finalPrice);

       if (isEligibleForDiscount(oldMembership.getClient(), oldMembership.getType())){
           newMembership.setDiscountApplied(true);
           newMembership.setPrice(finalPrice * 0.85);
       }

       Payment payment = new Payment();
       payment.setClient(oldMembership.getClient());
       payment.setMembership(newMembership);
       payment.setAmount(newMembership.getPrice());
       payment.setStatus(PaymentStatus.PENDING);
       payment.setPaymentDate(LocalDateTime.now());
       newMembership.setPayment(payment);

       Membership savedNewMembership = membershipRepo.save(newMembership);
       return toDto(savedNewMembership);
    }

    @Override
    public MembershipDto updatePayment(PaymentDto dto) {
        Payment payment = paymentRepo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        paymentRepo.save(payment);

        Membership membership = payment.getMembership();
        if (dto.getStatus() == PaymentStatus.COMPLETED){
            membership.setStatus(MembershipStatus.ACTIVE);
            membershipRepo.save(membership);
        }
        return toDto(membership);
    }

    @Override
    public MembershipDto getMembershipById(Long id) {
        Membership membership = membershipRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
        return toDto(membership);
    }

    @Override
    public List<MembershipDto> getMembershipsByClient(String clientUserName) {
        Client client = clientRepo.findByUserNameIgnoreCase(clientUserName)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return membershipRepo.findByClientId(client.getId()).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public double calculateTotalPrice(Membership membership){
        return calculateFinalPrice(membership);
    }


    private double calculateFinalPrice(Membership membership) {
        double total = membership.getPrice();

        if (membership.getTrainer() != null){
            total += membership.getTrainer().getPricePerMonth();
        }
        if (membership.getNutritionist() != null){
            total += membership.getNutritionist().getPricePerMonth();
        }
        if (membership.getFitnessClasses() != null){
            total += membership.getFitnessClasses().stream()
                    .mapToDouble(FitnessClass::getPrice)
                    .sum();
        }
        return total;
    }

    private boolean isEligibleForDiscount(Client client, MembershipType type) {
        List<Membership> lastThree = membershipRepo
                .findTopLastThreeByClientIdAndTypeOrderByEndDateDesc(client.getId(),type);
        return lastThree.size() == 3 && lastThree.stream()
                .allMatch(m -> m.getStatus() == MembershipStatus.ACTIVE); // ?? nu cred ca e ok, mai trebuie verificat!!
    }


    @Override
    public Membership toEntity(MembershipDto dto){
        Membership membership = new Membership();
        membership.setName(dto.getName());
        membership.setPrice(dto.getPrice());
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setType(dto.getType());
        membership.setStatus(dto.getStatus());
        membership.setDiscountApplied(dto.isDiscountApplied());

        if(dto.getClientUserName() != null && !dto.getClientUserName().isBlank()){
            Client client = clientRepo.findByUserNameIgnoreCase(dto.getClientUserName())
                    .orElseGet(() -> {
                        Client c = new Client();
                        c.setUserName(dto.getClientUserName());
                        return clientRepo.save(c);
                    });
            membership.setClient(client);
        }
        if(dto.getTrainerName() != null && !dto.getTrainerName().isBlank()){
            Trainer trainer = trainerRepo.findTrainerByNameIgnoreCase(dto.getTrainerName())
                    .orElseGet(() ->{
                        Trainer t = new Trainer();
                        t.setName(dto.getTrainerName());
                        return trainerRepo.save(t);
                    });
            membership.setTrainer(trainer);
        }
        if(dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
            Nutritionist nutritionist = nutritionistRepo.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                    .orElseGet(() ->{
                        Nutritionist n = new Nutritionist();
                        n.setName(dto.getNutritionistName());
                        return nutritionistRepo.save(n);
                    });
            membership.setNutritionist(nutritionist);
        }
        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .filter(n -> n!= null && !n.isBlank())
                    .map(name -> fitnessClassRepo.findFitnessClassByNameIgnoreCase(name)
                            .orElseGet(() ->{
                                FitnessClass f = new FitnessClass();
                                f.setName(name);
                                return fitnessClassRepo.save(f);
                            })
                    ).collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }
        if(dto.getFinalPrice() != null || dto.getPaymentDate() != null || dto.getPaymentStatus() != null){
            Payment payment = new Payment();
            payment.setAmount(dto.getFinalPrice());
            payment.setPaymentDate(dto.getPaymentDate());
            payment.setStatus(dto.getPaymentStatus());
            membership.setPayment(payment);
        }
        return membershipRepo.save(membership);
    }

    @Override
    public MembershipDto toDto (Membership membership){
        MembershipDto dto = new MembershipDto();
        dto.setId(membership.getId());
        dto.setName(membership.getName());
        dto.setPrice(membership.getPrice());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setType(membership.getType());
        dto.setStatus(dto.getStatus());
        dto.setDiscountApplied(membership.isDiscountApplied());

        if(membership.getClient() != null){
            dto.setClientUserName(membership.getClient().getUserName());
        }
        if(membership.getTrainer() != null){
            dto.setTrainerName(membership.getTrainer().getName());
        }
        if (membership.getNutritionist() != null){
            dto.setNutritionistName(membership.getNutritionist().getName());
        }
        if(membership.getFitnessClasses() != null){
            dto.setFitnessClasses(membership.getFitnessClasses().stream()
                    .map(FitnessClass::getName)
                    .collect(Collectors.toSet()));
        }
        if (membership.getPayment() != null){
            dto.setFinalPrice(membership.getPayment().getAmount());
            dto.setPaymentDate(membership.getPayment().getPaymentDate());
            dto.setPaymentStatus(membership.getPayment().getStatus());
        }
        return dto;
}}

