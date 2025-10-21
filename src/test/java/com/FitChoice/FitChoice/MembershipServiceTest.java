package com.FitChoice.FitChoice;


import com.FitChoice.FitChoice.model.dto.MembershipCreateDto;
import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.FitChoice.FitChoice.model.enums.PaymentStatus;
import com.FitChoice.FitChoice.repository.*;
import com.FitChoice.FitChoice.service.implementation.MembershipServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @Mock
    private NutritionistRepository nutritionistRepository;

    @Mock
    MembershipRepository membershipRepository;

    @Mock
    FitnessClassRepository fitnessClassRepository;

    @InjectMocks
    private MembershipServiceImplementation membershipService;

    private Client testClient;
    private Trainer testTrainer;
    private Nutritionist testNutritionist;


    @BeforeEach
    void setUp(){

        testClient = new Client();
        testClient.setId(1L);
        testClient.setUserName("Elena");
        testClient.setEmail("elena@gmail.com");

        testTrainer = new Trainer();
        testTrainer.setId(1L);
        testTrainer.setName("Bogdan");
        testTrainer.setPricePerMonth(500.0);

        testNutritionist = new Nutritionist();
        testNutritionist.setId(1L);
        testNutritionist.setName("Alin");
        testNutritionist.setPricePerMonth(150.0);

    }
@Test
    void createMembership_withTrainerAndNutritionist_shouldCalculatePriceCorrectly(){

        MembershipCreateDto dto = new MembershipCreateDto();
        dto.setClientUserName("Elena");
        dto.setType(MembershipType.FULLFITNESS);
        dto.setTrainerName("Bogdan");
        dto.setNutritionistName("Alin");

        when(clientRepository.findByUserNameIgnoreCase("Elena")).thenReturn(Optional.of(testClient));
        when(trainerRepository.findTrainerByNameIgnoreCase("Bogdan")).thenReturn(Optional.of(testTrainer));
        when(nutritionistRepository.findNutritionistByNameIgnoreCase("Alin")).thenReturn(Optional.of(testNutritionist));
        when(membershipRepository.findByClientIdOrderByEndDateDesc(1L)).thenReturn(Collections.emptyList());
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = membershipService.createMembership(dto);

        assertNotNull(result);
        assertEquals(800.0,result.getPrice(), 0.01);
        assertFalse(result.isDiscountApplied());
    }

    @Test
    void createMembership_shouldApplyDiscountWhenClientHasThreePreviousCompletedMemberships() {
        // Arrange
        MembershipCreateDto dto = new MembershipCreateDto();
        dto.setClientUserName("Elena");
        dto.setType(MembershipType.FULLFITNESS);

        // Simulăm cele 3 abonamente anterioare plătite de același tip
        List<Membership> lastThree = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Membership m = new Membership();
            m.setType(MembershipType.FULLFITNESS);
            Payment p = new Payment();
            p.setStatus(PaymentStatus.COMPLETED);
            m.setPayment(p);
            lastThree.add(m);
        }

        when(clientRepository.findByUserNameIgnoreCase("Elena")).thenReturn(Optional.of(testClient));
        when(membershipRepository.findByClientIdOrderByEndDateDesc(1L)).thenReturn(lastThree);
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act – creăm al patrulea abonament
        var result = membershipService.createMembership(dto);

        // Assert – reducerea trebuie aplicată
        assertTrue(result.isDiscountApplied());
        assertEquals(127.5, result.getPrice(), 0.01); // presupunând că prețul de bază e 150 și reducerea e 15%
    }


    @Test
    void createMembership_ShouldNotApplyDiscount_WhenNotAllPreviousArePaidOrDifferentType() {
        MembershipCreateDto dto = new MembershipCreateDto();
        dto.setClientUserName("elena");
        dto.setType(MembershipType.FULLFITNESS);

        Membership m1 = new Membership();
        m1.setType(MembershipType.FULLFITNESS);
        Payment p1 = new Payment();
        p1.setStatus(PaymentStatus.COMPLETED);
        m1.setPayment(p1);

        Membership m2 = new Membership();
        m2.setType(MembershipType.GYM_PRO); // alt tip!
        Payment p2 = new Payment();
        p2.setStatus(PaymentStatus.COMPLETED);
        m2.setPayment(p2);

        Membership m3 = new Membership();
        m3.setType(MembershipType.FULLFITNESS);
        Payment p3 = new Payment();
        p3.setStatus(PaymentStatus.PENDING);
        m3.setPayment(p3);

        List<Membership> lastThree = Arrays.asList(m1, m2, m3);

        when(clientRepository.findByUserNameIgnoreCase("elena")).thenReturn(Optional.of(testClient));
        when(membershipRepository.findByClientIdOrderByEndDateDesc(1L)).thenReturn(lastThree);
        when(membershipRepository.save(any(Membership.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = membershipService.createMembership(dto);

        assertFalse(result.isDiscountApplied());
        assertEquals(150.0, result.getPrice(), 0.01);
    }

}

