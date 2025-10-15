package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.repository.ClientRepository;
import com.FitChoice.FitChoice.repository.FitnessClassRepository;
import com.FitChoice.FitChoice.repository.NutritionistRepository;
import com.FitChoice.FitChoice.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    ClientRepository clientRepo;

    @Autowired
    TrainerRepository trainerRepo;

    @Autowired
    NutritionistRepository nutritionistRepo;

    @Autowired
    FitnessClassRepository fitnessClassRepo;

    public Membership toEntity(MembershipDto dto) {
        Membership membership = new Membership();

        membership.setType(dto.getType() != null ? dto.getType() : MembershipType.FULLFITNESS);
        membership.setBasePrice(dto.getBasePrice());
        membership.setFinalPrice(dto.getFinalPrice());
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setStatus(dto.getStatus() != null ? dto.getStatus() : MembershipStatus.INACTIVE);
        membership.setDiscountApplied(dto.getDiscountApplied());

        if (dto.getClientUserName() != null && !dto.getClientUserName().isBlank()) {
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
                    .orElseGet(() -> {
                        Trainer t = new Trainer();
                        t.setName(dto.getTrainerName());
                        return trainerRepo.save(t);
                    });
            membership.setTrainer(trainer);
        }
        if(dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
            Nutritionist nutritionist = nutritionistRepo.findNutritionistByNameIgnoreCase(dto.getNutritionistName())
                    .orElseGet(() -> {
                        Nutritionist n = new Nutritionist();
                        n.setName(dto.getNutritionistName());
                        return nutritionistRepo.save(n);
                    });
            membership.setNutritionist(nutritionist);
        }
        if(dto.getFitnessClasses() != null && !dto.getFitnessClasses().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClasses().stream()
                    .filter(n -> n != null && !n.isBlank())
                    .map(fitClassName -> fitnessClassRepo.findFitnessClassByNameIgnoreCase(fitClassName)
                            .orElseGet(() -> {
                                FitnessClass fClass = new FitnessClass();
                                fClass.setName(fitClassName);
                                return fitnessClassRepo.save(fClass);
                            })).collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);

        }
        if(dto.getPayAmount() != null || dto.getPaymentDate() != null || dto.getPaymentStatus() != null){
            Payment payment = new Payment();
            payment.setAmount(dto.getPayAmount());
            payment.setPaymentDate(dto.getPaymentDate());
            payment.setStatus(dto.getPaymentStatus());
            membership.setPayment(payment);
        }
    return membership;
    }

    public MembershipDto toDto (Membership membership){
        MembershipDto dto = new MembershipDto();
        dto.setId(membership.getId());
        dto.setBasePrice(membership.getBasePrice());
        dto.setFinalPrice(membership.getFinalPrice());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setType(membership.getType());
        dto.setStatus(membership.getStatus());
        dto.setDiscountApplied(membership.getDiscountApplied());

        if(membership.getClient() != null){
            dto.setClientUserName(membership.getClient().getUserName());
        }
        if (membership.getTrainer() != null){
            dto.setTrainerName(membership.getTrainer().getName());
        }
        if(membership.getNutritionist() != null){
            dto.setNutritionistName(membership.getNutritionist().getName());
        }
        if (membership.getFitnessClasses() != null){
            dto.setFitnessClasses(membership.getFitnessClasses().stream()
                    .map(FitnessClass :: getName)
                    .collect(Collectors.toSet()));
        }
        if (membership.getPayment() != null){
            dto.setPayAmount(membership.getPayment().getAmount());
            dto.setPaymentDate(membership.getPayment().getPaymentDate());
            dto.setPaymentStatus(membership.getPayment().getStatus());
        }
        return dto;
    }
}

