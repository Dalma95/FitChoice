package com.FitChoice.FitChoice;


import com.FitChoice.FitChoice.model.entity.*;
import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {

    private Membership testMembership;
    private Client testClient;
    private Trainer testTrainer;
    private Nutritionist testNutritionist;
    private Payment testPayment;

    @BeforeEach
    void setUp(){

        testClient = new Client();
        testClient.setId(1L);
        testClient.setUserName("elena");
        testClient.setEmail("elena@gmail.com");

        testTrainer = new Trainer();
        testTrainer.setId(1L);
        testTrainer.setName("Bogdan");
        testTrainer.setPricePerMonth(500.0);

        testNutritionist = new Nutritionist();
        testNutritionist.setId(1L);
        testNutritionist.setName("Alin");
        testNutritionist.setPricePerMonth(150.0);

        testMembership = new Membership();
        testMembership.setId(1L);
        testMembership.setPrice(650.0);
        testMembership.setStartDate(LocalDate.now());
        testMembership.setEndDate(LocalDate.now().plusMonths(1));
        testMembership.setType(MembershipType.FULLFITNESS);
        testMembership.setStatus(MembershipStatus.INACTIVE);
        testMembership.setDiscountApplied(false);
        testMembership.setClient(testClient);
        testMembership.setTrainer(testTrainer);
        testMembership.setNutritionist(null);
        testMembership.setFitnessClasses(null);





    }
}
