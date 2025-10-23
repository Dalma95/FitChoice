package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MembershipDto {

    private Long id;
    private Double price;
    private LocalDate startDate;
    private LocalDate  endDate;
    private MembershipType type;
    private MembershipStatus status;
    private boolean discountApplied;

    private String clientUserName;
    private String trainerName;
    private String nutritionistName;
    private Set<String> fitnessClasses;

    private Double finalPrice;
    private LocalDate paymentDate;
    private PaymentStatus paymentStatus;
}
