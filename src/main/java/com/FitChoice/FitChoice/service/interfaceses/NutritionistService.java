package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;

import java.util.List;
import java.util.Optional;

public interface NutritionistService {

    Nutritionist createNutritionist(Nutritionist nutritionist);

    List<Nutritionist> findAll();

    Optional<Nutritionist> findNutritionistById(Long id);

    Nutritionist updateNutritionist(Long id, Nutritionist nutritionist);

    void deleteNutritionistById(Long id);

    Nutritionist toEntity(NutritionistDto dto);

    NutritionistDto toDto(Nutritionist nutritionist);
}
