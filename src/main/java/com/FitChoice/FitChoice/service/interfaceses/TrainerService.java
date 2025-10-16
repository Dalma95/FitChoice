package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.TrainerDto;
import com.FitChoice.FitChoice.model.entity.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Trainer createTrainer(Trainer trainer);

    List<Trainer> findAllTrainers();

    Optional<Trainer> findTrainerById(Long id);

    void deleteTrainerById(Long id);

    Trainer toEntity (TrainerDto dto);
}
