package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.entity.Payment;
import com.FitChoice.FitChoice.model.enums.MembershipType;

import java.util.List;
import java.util.Optional;

public interface PaymentService {

    Payment createPaymentForMembership(Membership membership, double amount);

    Payment updatePayment(Payment payment);

    double calculateFinalPrice(Membership membership);

    boolean isEligibleForDiscount(Client client, MembershipType type);

    Optional<PaymentDto> getPaymentById(Long id);

    List<PaymentDto> getPaymentsByClient(String username);

    Payment toEntity (PaymentDto dto);

    PaymentDto toDto(Payment payment);
}
