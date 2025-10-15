package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
    Optional<Nutritionist> findByNameIgnoreCase(String nutritionistName);

    Optional<Nutritionist> findNutritionistByNameIgnoreCase(String nutritionistName);
}
