package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MembershipDto {

    private String type;
    private Double basePrice;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Boolean discountApplied;

    private String userName;          // identificator unic pentru client// id-ul antrenorului (daca exista)
    private String trainerName;
    private String nutritionistName;

    private Set<String> extraOptions;
    private Set<String> fitnessClassNames;

    private Boolean paymentStatus;    // true = platit, false = neplatit




}
