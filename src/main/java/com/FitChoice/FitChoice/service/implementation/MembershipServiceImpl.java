package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.dto.MembershipResponseDto;
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

import java.time.LocalDate;
import java.util.*;
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
    public MembershipDto createMembership(MembershipCreateDto dto) {

        Client client = clientRepo.findByUserNameIgnoreCase(dto.getClientUserName())
                .orElseThrow(() -> new RuntimeException("Client not found."));

        Membership membership = new Membership();
        membership.setClient(client);
        membership.setType(dto.getType() != null ? dto.getType() : MembershipType.FULLFITNESS);
        membership.setStatus(MembershipStatus.INACTIVE);
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusMonths(1));

        double basePrice =150.0;
        double trainerPrice= 500.0;
        double nutritionistPrice = 150.0;
        double finalPrice = basePrice;

        switch (membership.getType()){
            case GYM_PRO :
                Trainer trainer = trainerRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                membership.setTrainer(trainer);
                finalPrice += trainerPrice;
                break;

            case GYM_STAR:
                Trainer trainer1 = trainerRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                Nutritionist nutritionist = nutritionistRepo.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No nutritionists available"));
                membership.setTrainer(trainer1);
                membership.setNutritionist(nutritionist);
                finalPrice+= trainerPrice+nutritionistPrice;
                break;

            case FULLFITNESS:
                if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()){
                    Trainer chosenTrainer = trainerRepo.findTrainerByNameIgnoreCase(dto.getTrainerName())
                            .orElseThrow(() -> new RuntimeException("Trainer not found"));
                    finalPrice+= chosenTrainer.getPricePerMonth();
                    membership.setTrainer(chosenTrainer);

                }
                if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
                    Nutritionist chosenNutritionist = nutritionistRepo.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                            .orElseThrow(() -> new RuntimeException("Nutritionist not found"));
                    finalPrice+= chosenNutritionist.getPricePerMonth();
                    membership.setNutritionist(chosenNutritionist);

                }
                break;
        }
        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .map(name -> fitnessClassRepo.findFitnessClassByNameIgnoreCase(name)
                            .orElseThrow(() -> new RuntimeException("Class not found ")))
                    .collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);

            double fitnessClassesTotal = fitnessClasses.stream()
                    .mapToDouble(FitnessClass::getPrice)
                    .sum();

            finalPrice += fitnessClassesTotal;

        }else {
            membership.setFitnessClasses(null);
        }

        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        int recentCount = membershipRepo.countByClient_IdAndEndDateAfter(client.getId(), threeMonthsAgo);
        if (recentCount >= 3) {
            membership.setDiscountApplied(true);
            finalPrice = finalPrice * 0.85;
        }

        membership.setPrice(finalPrice);

        Payment payment = new Payment();
        payment.setClient(client);
        payment.setMembership(membership);
        payment.setAmount(finalPrice);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDate.now());
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
        if (oldMembership.getFitnessClasses() != null) {
            newMembership.setFitnessClasses(new HashSet<>(oldMembership.getFitnessClasses()));
        }
       newMembership.setType(oldMembership.getType());
       newMembership.setPrice(oldMembership.getPrice());
       newMembership.setStartDate(LocalDate.now());
       newMembership.setEndDate(LocalDate.now().plusMonths(1));
       newMembership.setStatus(MembershipStatus.INACTIVE);

       double finalPrice = calculateFinalPrice(newMembership);

        if (isEligibleForDiscount(oldMembership.getClient(), oldMembership.getType())) {
            newMembership.setDiscountApplied(true);
            newMembership.setPrice(finalPrice * 0.85);
        } else {
            newMembership.setDiscountApplied(false);
            newMembership.setPrice(finalPrice);
        }
        newMembership.setPrice(finalPrice);

       Payment payment = new Payment();
       payment.setClient(oldMembership.getClient());
       payment.setMembership(newMembership);
       payment.setAmount(newMembership.getPrice());
       payment.setStatus(PaymentStatus.PENDING);
       payment.setPaymentDate(LocalDate.now());
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
        if (dto.getStatus() == PaymentStatus.COMPLETED) {
            membership.setStatus(MembershipStatus.ACTIVE);
            membership.setStartDate(LocalDate.now());
            membership.setEndDate(LocalDate.now().plusMonths(1));
        }else {
            membership.setStatus(MembershipStatus.INACTIVE);
        }
        membershipRepo.save(membership);
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

//    @Override
//    public double calculateTotalPrice(Membership membership){
//        return calculateFinalPrice(membership);
//    }


    private double calculateFinalPrice(Membership membership) {
        double total = membership.getPrice();

        if (membership.getTrainer() != null) total += membership.getTrainer().getPricePerMonth();
        if (membership.getNutritionist() != null) total += membership.getNutritionist().getPricePerMonth();
        if (membership.getFitnessClasses() != null)
            total += membership.getFitnessClasses().stream()
                    .mapToDouble(FitnessClass::getPrice)
                    .sum();
        return total;
    }

    private boolean isEligibleForDiscount(Client client, MembershipType type) {
        List<Membership> lastThreeSameType = membershipRepo
                .findTop3ByClientIdAndTypeOrderByEndDateDesc(client.getId(), type);

        return lastThreeSameType.size() == 3 &&
                lastThreeSameType.stream()
                        .allMatch(m -> m.getPayment() != null &&
                                m.getPayment().getStatus() == PaymentStatus.COMPLETED);}


    @Override
    public Membership toEntity(MembershipDto dto){
        Membership membership = new Membership();
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

