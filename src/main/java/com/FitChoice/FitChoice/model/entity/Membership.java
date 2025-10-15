package com.FitChoice.FitChoice.model.entity;

import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private MembershipType type = MembershipType.FULLFITNESS;
    private Double basePrice;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private MembershipStatus status = MembershipStatus.INACTIVE;
    private Boolean discountApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id")
    private Nutritionist nutritionist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "membership_fitness_classes",
            joinColumns = @JoinColumn(name ="membership_id"),
            inverseJoinColumns = @JoinColumn(name = "fitness_class_id")
    )
    private Set<FitnessClass> fitnessClasses=new HashSet<>();

    @OneToOne(mappedBy = "membership", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Payment payment;
}
