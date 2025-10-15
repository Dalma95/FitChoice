package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.PersonalTrainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonalTrainerRepository extends JpaRepository<PersonalTrainer, Long> {
    Optional<PersonalTrainer> findByNameIgnoreCase(String trainerName);
}
