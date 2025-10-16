package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.enums.MembershipType;
import lombok.Data;

import java.util.Set;

@Data
public class MembershipCreateDto {

    private String clientUserName;
    private MembershipType type = MembershipType.FULLFITNESS;
    private Double price;
    private String trainerName;
    private String nutritionistName;
    private Set<String> fitnessClasses;
}
