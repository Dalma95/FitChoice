package com.FitChoice.FitChoice.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nutritionists")
public class Nutritionist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private Double pricePerMonth;

    @JsonIgnore
    private String role = "NUTRITIONIST";

    @OneToMany(mappedBy = "nutritionist" )
    private List<Membership> memberships;


}
