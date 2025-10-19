package com.FitChoice.FitChoice.model.entity;

import com.FitChoice.FitChoice.model.enums.MembershipStatus;
import com.FitChoice.FitChoice.model.enums.MembershipType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @EqualsAndHashCode.Include
    private Long id;

    private Double price;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private MembershipType type = MembershipType.FULLFITNESS;

    @Enumerated(EnumType.STRING)
    private MembershipStatus status = MembershipStatus.INACTIVE;

    private boolean discountApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @JsonIgnore
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    @JsonIgnore
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutritionist_id")
    @JsonIgnore
    private Nutritionist nutritionist;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "membership_fitness_classes",
            joinColumns = @JoinColumn(name ="membership_id"),
            inverseJoinColumns = @JoinColumn(name = "fitness_class_id")
    )
    @JsonIgnore
    private Set<FitnessClass> fitnessClasses=new HashSet<>();

    @OneToOne(mappedBy = "membership", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
}
