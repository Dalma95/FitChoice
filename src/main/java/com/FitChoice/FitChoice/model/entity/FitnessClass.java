package com.FitChoice.FitChoice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"memberships"})
@Entity
@Table(name = "fitness_classes")
public class FitnessClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;
    private Integer capacity;
    private Double price;

    @ManyToMany(mappedBy = "fitnessClasses", fetch = FetchType.LAZY)
    private Set<Membership> memberships=new HashSet<>();

}
