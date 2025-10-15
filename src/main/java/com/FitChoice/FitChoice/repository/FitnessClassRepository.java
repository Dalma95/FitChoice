package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FitnessClassRepository extends JpaRepository<FitnessClass, Long> {
    Optional<FitnessClass> findByNameIgnoreCase(String name);

    Optional<FitnessClass> findFitnessClassByNameIgnoreCase(String fitnessClassName);
}
