package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;
import org.springframework.stereotype.Component;

@Component
public class NutritionistMapper {

    public NutritionistDto toDto(Nutritionist nutritionist){
        if (nutritionist == null) return null;
        NutritionistDto dto = new NutritionistDto();
        dto.setName(nutritionist.getName());
        dto.setPhoneNumber(nutritionist.getPhoneNumber());
        dto.setPricePerMonth(nutritionist.getPricePerMonth());
        return dto;
    }

    public Nutritionist toEntity(NutritionistDto dto){
        if (dto == null) return null;
        Nutritionist nutritionist = new Nutritionist();
        nutritionist.setName(dto.getName());
        nutritionist.setPricePerMonth(dto.getPricePerMonth());
        nutritionist.setPhoneNumber(dto.getPhoneNumber());
        return nutritionist;
    }
}
