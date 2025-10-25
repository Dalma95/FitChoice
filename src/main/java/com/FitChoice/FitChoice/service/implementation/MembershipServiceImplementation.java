package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import com.FitChoice.FitChoice.repository.*;
import com.FitChoice.FitChoice.service.interfaceses.MembershipService;
import com.FitChoice.FitChoice.service.interfaceses.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MembershipServiceImplementation implements MembershipService {

    private final ClientRepository clientRepository;
    private final TrainerRepository trainerRepository;
    private final NutritionistRepository nutritionistRepository;
    private final FitnessClassRepository fitnessClassRepository;
    private final MembershipRepository membershipRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;

    @Override
    public Membership createMembership(MembershipCreateDto dto) {

        Client client = clientRepository.findByUserNameIgnoreCase(dto.getClientUserName())
                .orElseThrow(() -> new RuntimeException("Client not found."));

        Membership membership = new Membership();
        membership.setClient(client);
        membership.setType(dto.getType() != null ? dto.getType() : MembershipType.FULLFITNESS);
        membership.setStatus(MembershipStatus.INACTIVE);
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusMonths(1));


        switch (membership.getType()){
            case GYM_PRO :
                Trainer defaulTrainer = trainerRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                membership.setTrainer(defaulTrainer);
                break;

            case GYM_STAR:
                Trainer defaulTrainer2 = trainerRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No trainers available."));
                Nutritionist defaultNutritionist = nutritionistRepository.findAll().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("No nutritionists available"));
                membership.setTrainer(defaulTrainer2);
                membership.setNutritionist(defaultNutritionist);
                break;

            case FULLFITNESS:
            default:
                if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()) {
                    Trainer chosenTrainer = trainerRepository.findTrainerByNameIgnoreCase(dto.getTrainerName())
                            .orElseThrow(() -> new RuntimeException("Trainer not found."));
                    membership.setTrainer(chosenTrainer);
                }
                if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()) {
                    Nutritionist chosenNutritionist = nutritionistRepository.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                            .orElseThrow(() -> new RuntimeException("Nutritionist not found."));
                    membership.setNutritionist(chosenNutritionist);
                }
                break;
        }

        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .map(name -> fitnessClassRepository.findFitnessClassByNameIgnoreCase(name)
                            .orElseThrow(() -> new RuntimeException("Class not found ")))
                    .collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }else {
            membership.setFitnessClasses(null);
        }

        double finalPrice = paymentService.calculateFinalPrice(membership);

        if (paymentService.isEligibleForDiscount(client, membership.getType())) {
            membership.setDiscountApplied(true);
            finalPrice *= 0.85; // reducere 15%
        } else {
            membership.setDiscountApplied(false);
        }

        membership.setPrice(finalPrice);

        Payment payment = paymentService.createPaymentForMembership(membership, finalPrice);
        membership.setPayment(payment);

        Membership savedMembership = membershipRepository.save(membership);

        return savedMembership;
    }


    @Override
    public Membership renewMembership(Long membershipId) {
       Membership oldMembership = membershipRepository.findById(membershipId)
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

       double finalPrice = paymentService.calculateFinalPrice(newMembership);

        if (paymentService.isEligibleForDiscount(oldMembership.getClient(), oldMembership.getType())) {
            newMembership.setDiscountApplied(true);
            finalPrice *= 0.85;
        } else {
            newMembership.setDiscountApplied(false);
        }
        newMembership.setPrice(finalPrice);

        Payment payment = paymentService.createPaymentForMembership(newMembership, finalPrice);
        newMembership.setPayment(payment);

       Membership savedNewMembership = membershipRepository.save(newMembership);
       return savedNewMembership;
    }

    @Override
    public Optional<MembershipDto> getMembershipById(Long id) {
        return membershipRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public List<MembershipDto> getMembershipsByClient(String clientUserName) {
        Client client = clientRepository.findByUserNameIgnoreCase(clientUserName)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return membershipRepository.findByClientId(client.getId()).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Membership updateMembership(String username, Long id, MembershipCreateDto dto) {
        Client client = clientRepository.findByUserNameIgnoreCase(username)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Membership existingMembership = membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));

        // verificăm că aparține clientului
        if (!existingMembership.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("This membership does not belong to user: " + username);
        }

        // verificăm plata
        Payment payment = existingMembership.getPayment();
        if (payment != null && payment.getStatus() == PaymentStatus.COMPLETED) {
            throw new RuntimeException("You cannot modify membership details after payment is completed.");
        }

        // actualizăm tipul
        if (dto.getType() != null) {
            existingMembership.setType(dto.getType());
        }

        // actualizăm trainerul (dacă există în DTO)
        if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()) {
            Trainer trainer = trainerRepository.findTrainerByNameIgnoreCase(dto.getTrainerName())
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
            existingMembership.setTrainer(trainer);
        } else {
            existingMembership.setTrainer(null);
        }

        // actualizăm nutriționistul
        if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()) {
            Nutritionist nutritionist = nutritionistRepository.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                    .orElseThrow(() -> new RuntimeException("Nutritionist not found"));
            existingMembership.setNutritionist(nutritionist);
        } else {
            existingMembership.setNutritionist(null);
        }

        // actualizăm clasele
        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()) {
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .map(name -> fitnessClassRepository.findFitnessClassByNameIgnoreCase(name)
                            .orElseThrow(() -> new RuntimeException("Fitness class not found: " + name)))
                    .collect(Collectors.toSet());
            existingMembership.setFitnessClasses(fitnessClasses);
        } else {
            existingMembership.setFitnessClasses(new HashSet<>());
        }

        // recalculăm prețul
        double newPrice = paymentService.calculateFinalPrice(existingMembership);
        existingMembership.setPrice(newPrice);

        // actualizăm statusul
        existingMembership.setStatus(MembershipStatus.INACTIVE);

        // actualizăm și payment-ul
        if (existingMembership.getPayment() != null) {
            existingMembership.getPayment().setAmount(newPrice);
            existingMembership.getPayment().setStatus(PaymentStatus.PENDING);
            paymentRepository.save(existingMembership.getPayment());
        } else {
            Payment newPayment = paymentService.createPaymentForMembership(existingMembership, newPrice);
            existingMembership.setPayment(newPayment);
        }

        return membershipRepository.save(existingMembership);
    }

    @Override
    public void deleteByMembershipIdAndClientUserName(Long id, String clientUserName) {
        Client client = clientRepository.findByUserNameIgnoreCase(clientUserName)
                .orElseThrow(() -> new RuntimeException("Client not found with username: " + clientUserName));

        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found with id: " + id));

        if (!membership.getClient().getId().equals(client.getId())) {
            throw new RuntimeException("This membership does not belong to user: " + clientUserName);
        }

        membershipRepository.delete(membership);

    }

    @Override
    public void deleteAllMembershipsByClientId(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        membershipRepository.deleteAllMembershipsByClientId(id);
    }

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
            Client client = clientRepository.findByUserNameIgnoreCase(dto.getClientUserName())
                    .orElseGet(() -> {
                        Client c = new Client();
                        c.setUserName(dto.getClientUserName());
                        return clientRepository.save(c);
                    });
            membership.setClient(client);
        }
        if(dto.getTrainerName() != null && !dto.getTrainerName().isBlank()){
            Trainer trainer = trainerRepository.findTrainerByNameIgnoreCase(dto.getTrainerName())
                    .orElseGet(() ->{
                        Trainer t = new Trainer();
                        t.setName(dto.getTrainerName());
                        return trainerRepository.save(t);
                    });
            membership.setTrainer(trainer);
        }
        if(dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
            Nutritionist nutritionist = nutritionistRepository.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                    .orElseGet(() ->{
                        Nutritionist n = new Nutritionist();
                        n.setName(dto.getNutritionistName());
                        return nutritionistRepository.save(n);
                    });
            membership.setNutritionist(nutritionist);
        }
        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .filter(n -> n!= null && !n.isBlank())
                    .map(name -> fitnessClassRepository.findFitnessClassByNameIgnoreCase(name)
                            .orElseGet(() ->{
                                FitnessClass f = new FitnessClass();
                                f.setName(name);
                                return fitnessClassRepository.save(f);
                            })
                    ).collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }
        if(dto.getFinalPrice() != null && dto.getPaymentDate() != null && dto.getPaymentStatus() != null){
            Payment payment = new Payment();
            payment.setClient(membership.getClient());
            payment.setAmount(dto.getFinalPrice());
            payment.setPaymentDate(dto.getPaymentDate());
            payment.setStatus(dto.getPaymentStatus());
            membership.setPayment(payment);
        }
        return membershipRepository.save(membership);
    }

    @Override
    public MembershipDto toDto (Membership membership){
        MembershipDto dto = new MembershipDto();
        dto.setId(membership.getId());
        dto.setPrice(membership.getPrice());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setType(membership.getType());
        dto.setStatus(membership.getStatus());
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
}

    @Override
    public Membership toEntityFromCreateDto(MembershipCreateDto dto) {
        Membership membership = new Membership();

        membership.setType(dto.getType());
        membership.setStatus(MembershipStatus.INACTIVE);
        membership.setDiscountApplied(false);

        if (dto.getClientUserName() != null && !dto.getClientUserName().isBlank()) {
            Client client = clientRepository.findByUserNameIgnoreCase(dto.getClientUserName())
                    .orElseGet(() -> {
                        Client c = new Client();
                        c.setUserName(dto.getClientUserName());
                        return clientRepository.save(c);
                    });
            membership.setClient(client);
        }

        if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()) {
            Trainer trainer = trainerRepository.findTrainerByNameIgnoreCase(dto.getTrainerName())
                    .orElseGet(() -> {
                        Trainer t = new Trainer();
                        t.setName(dto.getTrainerName());
                        return trainerRepository.save(t);
                    });
            membership.setTrainer(trainer);
        }

        if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()) {
            Nutritionist nutritionist = nutritionistRepository.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                    .orElseGet(() -> {
                        Nutritionist n = new Nutritionist();
                        n.setName(dto.getNutritionistName());
                        return nutritionistRepository.save(n);
                    });
            membership.setNutritionist(nutritionist);
        }

        if (dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()) {
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .filter(n -> n != null && !n.isBlank())
                    .map(name -> fitnessClassRepository.findFitnessClassByNameIgnoreCase(name)
                            .orElseGet(() -> {
                                FitnessClass f = new FitnessClass();
                                f.setName(name);
                                return fitnessClassRepository.save(f);
                            })
                    ).collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }

        return membershipRepository.save(membership);
    }
    }



