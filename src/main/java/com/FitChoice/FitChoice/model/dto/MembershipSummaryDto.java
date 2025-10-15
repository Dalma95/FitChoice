package com.FitChoice.FitChoice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MembershipSummaryDto {

    private String userName;
    private String type;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
}
