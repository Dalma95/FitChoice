package com.FitChoice.FitChoice;

import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.repository.*;
import com.FitChoice.FitChoice.service.implementation.MembershipServiceImplementation;
import com.FitChoice.FitChoice.service.interfaceses.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MembershipServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private MembershipRepository membershipRepository;
    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private MembershipServiceImplementation membershipService;

    private Client client;
    private Membership membership;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();
        client.setId(1L);
        client.setUserName("testUser");

        membership = new Membership();
        membership.setId(10L);
        membership.setClient(client);

        payment = new Payment();
        payment.setId(100L);
        payment.setClient(client);
        payment.setAmount(150.0);
    }

    @Test
    void testCreateMembership_ShouldReturnSuccess() {
        MembershipCreateDto dto = new MembershipCreateDto();
        dto.setClientUserName("testUser");
        dto.setType(MembershipType.FULLFITNESS);

        when(clientRepository.findByUserNameIgnoreCase("testUser")).thenReturn(Optional.of(client));
        when(paymentService.isEligibleForDiscount(any(), any())).thenReturn(false);
        when(paymentService.createPaymentForMembership(any(), anyDouble())).thenReturn(payment);
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Membership result = membershipService.createMembership(dto);

        assertNotNull(result);
        assertEquals(client, result.getClient());
        assertEquals(MembershipStatus.INACTIVE, result.getStatus());
        assertEquals(150.0, result.getPrice());
        verify(paymentService).createPaymentForMembership(any(), anyDouble());
    }

    @Test
    void testRenewMembership_ShouldReturnSuccess() {
        membership.setType(MembershipType.GYM_PRO);
        membership.setPrice(150.0);
        membership.setPayment(payment);
        when(membershipRepository.findById(10L)).thenReturn(Optional.of(membership));
        when(paymentService.calculateFinalPrice(any())).thenReturn(150.0);
        when(paymentService.createPaymentForMembership(any(), anyDouble())).thenReturn(payment);
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Membership result = membershipService.renewMembership(10L);

        assertNotNull(result);
        assertEquals(MembershipStatus.INACTIVE, result.getStatus());
        verify(paymentService).createPaymentForMembership(any(), anyDouble());
    }

    @Test
    void testGetMembershipsByClient_ShouldReturnSuccess() {
        membership.setId(20L);
        membership.setStatus(MembershipStatus.ACTIVE);

        when(clientRepository.findByUserNameIgnoreCase("testUser")).thenReturn(Optional.of(client));
        when(membershipRepository.findByClientId(1L)).thenReturn(List.of(membership));

        var result = membershipService.getMembershipsByClient("testUser");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getClientUserName());
        verify(membershipRepository).findByClientId(1L);
    }

}
