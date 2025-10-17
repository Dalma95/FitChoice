package com.FitChoice.FitChoice.model.dto;

import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PaymentDto {

    private Long id;
    private Long membershipId;
    private Double amount;
    private LocalDate paymentDate;
    private PaymentStatus status;


}
