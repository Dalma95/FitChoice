package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MembershipResponseDto {
    private Long id;
    private String clientUserName;
    private MembershipType type;
    private double price;
    private boolean discountApplied;
    private MembershipStatus status; // mereu INACTIVE la creare
    private LocalDate startDate;
    private LocalDate endDate;
    private PaymentStatus paymentStatus;
    private LocalDate paymentDate;
    private Set<String> fitnessClasses;
    private String trainerName;
    private String nutritionistName;
}
//??????
