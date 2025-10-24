package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;

import java.util.List;
import java.util.Optional;

public interface FitnessClassService {

    FitnessClass createFitnessClass(FitnessClass fitnessClass);

    List<FitnessClassDto> findAllFitnessClasses();

    Optional<FitnessClassDto> findClassById(Long id);

    FitnessClass updateFitnessClass(Long id, FitnessClass fitnessClass);

    void deleteClassById(Long id);

    FitnessClassDto toDto (FitnessClass fitnessClass);

    FitnessClass toEntity(FitnessClassDto dto);
}
