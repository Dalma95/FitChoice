package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    Optional<Trainer> findByNameIgnoreCase(String trainerName);

    Optional<Trainer> findTrainerByNameIgnoreCase(String trainerName);
}
