package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.MembershipDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MembershipMapper {

    @Autowired
    MembershipRepository membershipRepo;

    @Autowired
    ClientRepository clientRepo;

    @Autowired
    PersonalTrainerRepository personalTrainerRepo;

    @Autowired
    NutritionistRepository nutritionistRepo;

    @Autowired
    ExtraOptionRepository extraOptionRepo;

    @Autowired
    FitnessClassRepository fitnessClassRepo;

    public Membership toEntity(MembershipDto dto){
        Membership membership = new Membership();
        membership.setType(dto.getType());
        membership.setBasePrice(dto.getBasePrice());
        membership.setFinalPrice(dto.getFinalPrice());
        membership.setStartDate(dto.getStartDate());
        membership.setEndDate(dto.getEndDate());
        membership.setIsActive(dto.getIsActive());
        membership.setDiscountApplied(dto.getDiscountApplied());

        if (dto.getUserName() != null && !dto.getUserName().isBlank()){
            Client client = clientRepo.findByNameIgnoreCase(dto.getUserName())
                    .orElseGet(() -> {
                        Client c = new Client();
                        c.setUserName(dto.getUserName());
                        return clientRepo.save(c);
                    });
            membership.setClient(client);
        }
        if (dto.getTrainerName() != null && !dto.getTrainerName().isBlank()){
            PersonalTrainer trainer = personalTrainerRepo.findByNameIgnoreCase(dto.getTrainerName())
                    .orElseGet(() -> {
                        PersonalTrainer p = new PersonalTrainer();
                        p.setName(dto.getTrainerName());
                        return personalTrainerRepo.save(p);
                    });
            membership.setPersonalTrainer(trainer);
        }
        if (dto.getNutritionistName() != null && !dto.getNutritionistName().isBlank()){
            Nutritionist nutritionist = nutritionistRepo.findByNameIgnoreCase(dto.getNutritionistName())
                    .orElseGet(() -> {
                        Nutritionist n = new Nutritionist();
                        n.setName(dto.getNutritionistName());
                        return nutritionistRepo.save(n);
                    });
            membership.setNutritionist(nutritionist);
        }

        if (dto.getExtraOptions() != null && !dto.getExtraOptions().isEmpty()){
            Set<ExtraOption> extraOptions =dto.getExtraOptions().stream()
                    .filter(n -> n != null && !n.isBlank())
                    .map( name ->
                        extraOptionRepo.findByNameIgnoreCase(name)
                                .orElseGet(() -> {
                                    ExtraOption e = new ExtraOption();
                                    e.setName(name);
                                    return extraOptionRepo.save(e);
                                })
                    ).collect(Collectors.toSet());
            membership.setExtraOptions(extraOptions);
        }
        if (dto.getFitnessClassNames() != null && !dto.getFitnessClassNames().isEmpty()){
            Set<FitnessClass> fitnessClasses = dto.getFitnessClassNames().stream()
                    .filter(n -> n!= null && !n.isBlank())
                    .map(name ->
                            fitnessClassRepo.findByNameIgnoreCase(name)
                                    .orElseGet(() ->{
                                        FitnessClass f = new FitnessClass();
                                        f.setName(name);
                                        return fitnessClassRepo.save(f);
                                    })
                    ).collect(Collectors.toSet());
            membership.setFitnessClasses(fitnessClasses);
        }
        return membershipRepo.save(membership);
    }

    public MembershipDto toDto(Membership membership){
        MembershipDto dto  = new MembershipDto();
        dto.setType(membership.getType());
        dto.setBasePrice(membership.getBasePrice());
        dto.setFinalPrice(membership.getFinalPrice());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setIsActive(membership.getIsActive());
        dto.setDiscountApplied(membership.getDiscountApplied());

        if(membership.getClient() != null){
            dto.setUserName(membership.getClient().getUserName());
        }
        if(membership.getPersonalTrainer() != null){
            dto.setTrainerName(membership.getPersonalTrainer().getName());
        }
        if(membership.getNutritionist() != null){
            dto.setNutritionistName(membership.getNutritionist().getName());
        }
        if(membership.getExtraOptions() !=null) dto.setExtraOptions(membership.getExtraOptions());
        }

        }
}
