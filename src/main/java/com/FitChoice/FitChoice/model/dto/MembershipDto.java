package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MembershipDto {

    private Long id;
    private String name;
    private Double basePrice;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MembershipType type = MembershipType.FULLFITNESS;
    private MembershipStatus status = MembershipStatus.INACTIVE;
    private Boolean discountApplied;

    private Long clientId;
    private String clientUserName;

    private Long trainerId;
    private String trainerName;

    private Long nutritionistId;
    private String nutritionistName;

    private Set<String> fitnessClasses;

    private Double payAmount;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
}
