package com.FitChoice.FitChoice.model.entity;

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

    private String type;
    private Double basePrice;
    private Double finalPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private Boolean discountApplied;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany
    @JoinTable(
            name = "membership_plans",
            joinColumns = @JoinColumn(name= "membership_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_option_id")
    )
    private Set<ExtraOption> extraOptions=new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "membership_plans",
            joinColumns = @JoinColumn(name ="membership_id"),
            inverseJoinColumns = @JoinColumn(name = "fitness_class_id")
    )
    private Set<FitnessClass> fitnessClasses=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private PersonalTrainer personalTrainer;

    @ManyToOne
    @JoinColumn(name = "nutritionist_id")
    private Nutritionist nutritionist;

    @OneToOne
    private Payment payment;
}
