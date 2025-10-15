package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;

import java.util.List;
import java.util.Optional;

public interface FitnessClassService {

    FitnessClass createFitnessClass(FitnessClassDto fitnessClassDto);

    List<FitnessClass> findAllFitnessClasses();

    Optional<FitnessClass> findClassById(Long id);

    FitnessClass updateFitnessClass(Long id, FitnessClass fitnessClass);

    void deleteClassById(Long id);

    public default FitnessClassDto toDto(FitnessClass fitnessClass){
        FitnessClassDto dto = new FitnessClassDto();

        dto.setName(fitnessClass.getName());
        dto.setCapacity(fitnessClass.getCapacity());
        dto.setPrice(fitnessClass.getPrice());
        return dto;
    }

    public default FitnessClass toEntity(FitnessClassDto dto){
        FitnessClass fitnessClass = new FitnessClass();
        fitnessClass.setName(dto.getName());
        fitnessClass.setCapacity(dto.getCapacity());
        fitnessClass.setPrice(dto.getPrice());
        return fitnessClass;
    }
}
