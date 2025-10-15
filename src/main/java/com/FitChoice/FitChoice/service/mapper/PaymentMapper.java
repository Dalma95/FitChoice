package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDto toDto(Payment payment){
        PaymentDto dto = new PaymentDto();
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setPaymentDate(payment.getPaymentDate());
        return dto;
    }

    public Payment toEntity(PaymentDto dto){
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setStatus(dto.getStatus());
        payment.setPaymentDate(dto.getPaymentDate());
        return  payment;
    }
}
