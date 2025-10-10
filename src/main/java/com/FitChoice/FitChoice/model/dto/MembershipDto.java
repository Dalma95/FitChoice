package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.entity.ExtraOption;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MembershipDto {

    private String type;
    private Double basePrice;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Boolean discountApplied;

    private String clientId;
    private String trainerName;
    private String nutritionistName;
    private List<ExtraOption> extraOptions;
    private List<String> fitnessClassNames;
    private Boolean paymentStatus;




}
