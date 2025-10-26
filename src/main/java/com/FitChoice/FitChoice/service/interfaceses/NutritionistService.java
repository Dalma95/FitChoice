package com.FitChoice.FitChoice.service.interfaceses;

import com.FitChoice.FitChoice.model.dto.NutritionistCreateDto;
import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;

import java.util.List;
import java.util.Optional;

public interface NutritionistService {

    Nutritionist createNutritionist(Nutritionist nutritionist);

    List<NutritionistDto> findAll();

    Optional<NutritionistDto> findNutritionistById(Long id);

    Nutritionist updateNutritionist(Long id, Nutritionist nutritionist);

    void deleteNutritionistById(Long id);

    Nutritionist toEntity(NutritionistCreateDto dto);

    NutritionistDto toDto(Nutritionist nutritionist);
}
