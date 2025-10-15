package com.FitChoice.FitChoice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extra_options")
public class ExtraOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @ManyToMany(mappedBy = "extraOptions",fetch = FetchType.LAZY)
    private Set<Membership> memberships=new HashSet<>();
}
