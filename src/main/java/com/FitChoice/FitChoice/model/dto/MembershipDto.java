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
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MembershipType type;
    private MembershipStatus status;
    private boolean discountApplied;

    private String clientUserName;
    private String trainerName;
    private String nutritionistName;
    private Set<String> fitnessClasses;

    private Double finalPrice;
    private LocalDateTime paymentDate;
    private PaymentStatus paymentStatus;
}
