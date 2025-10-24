package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.PaymentDto;
import com.FitChoice.FitChoice.model.entity.Client;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.model.entity.Membership;
import com.FitChoice.FitChoice.model.entity.Payment;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import com.FitChoice.FitChoice.repository.ClientRepository;
import com.FitChoice.FitChoice.repository.MembershipRepository;
import com.FitChoice.FitChoice.repository.PaymentRepository;
import com.FitChoice.FitChoice.service.interfaceses.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImplementation implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    MembershipRepository membershipRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Payment createPaymentForMembership(Membership membership, double amount) {
        Payment payment = new Payment();
        payment.setClient(membership.getClient());
        payment.setMembership(membership);
        payment.setAmount(amount);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDate.now());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment) {
        Payment updatedPayment = paymentRepository.findById(payment.getId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        updatedPayment.setStatus(payment.getStatus());
        updatedPayment.setPaymentDate(payment.getPaymentDate());
        paymentRepository.save(updatedPayment);

        // Actualizăm și membership-ul
        Membership membership = updatedPayment.getMembership();
        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            membership.setStatus(MembershipStatus.ACTIVE);
            membership.setStartDate(LocalDate.now());
            membership.setEndDate(LocalDate.now().plusMonths(1));
        } else {
            membership.setStatus(MembershipStatus.INACTIVE);
        }
        membershipRepository.save(membership);

        return updatedPayment;
    }


    @Override
    public double calculateFinalPrice(Membership membership) {
        double basePrice = 150.0;
        double total = basePrice;

        if (membership.getTrainer() != null)
            total += membership.getTrainer().getPricePerMonth();

        if (membership.getNutritionist() != null)
            total += membership.getNutritionist().getPricePerMonth();

        if (membership.getFitnessClasses() != null)
            total += membership.getFitnessClasses()
                    .stream()
                    .mapToDouble(FitnessClass::getPrice)
                    .sum();

        return total;
    }

    @Override
    public boolean isEligibleForDiscount(Client client, MembershipType type) {
        List<Membership> memberships = membershipRepository.findByClientIdOrderByEndDateDesc(client.getId());
        int consecutivePaidSameType = 0;

        for (Membership m : memberships) {
            if (m.getType() == type &&
                    m.getPayment() != null &&
                    m.getPayment().getStatus() == PaymentStatus.COMPLETED) {
                consecutivePaidSameType++;
                if (consecutivePaidSameType == 3) return true;
            } else {
                consecutivePaidSameType = 0;
            }
        }
        return false;
    }

    @Override
    public Optional<PaymentDto> getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public List<PaymentDto> getPaymentsByClient(String username) {
        Client client = clientRepository.findByUserNameIgnoreCase(username).orElseThrow(() -> new RuntimeException("Client not found"));

        return paymentRepository.findByClientId(client.getId()).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Payment toEntity(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount());
        payment.setPaymentDate(dto.getPaymentDate());
        payment.setStatus(dto.getStatus());

        if (dto.getMembershipId() != null){
            Membership membership = membershipRepository.findById(dto.getMembershipId())
                    .orElseThrow(() -> new RuntimeException("Membership not found with id: " + dto.getMembershipId()));
            payment.setMembership(membership);
            payment.setClient(membership.getClient());
        }
        return payment;
    }

    @Override
    public PaymentDto toDto(Payment payment) {
        PaymentDto dto = new PaymentDto();

        dto.setId(payment.getId());
        dto.setMembershipId(payment.getMembership().getId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setStatus(payment.getStatus());

        return dto;
    }


}
