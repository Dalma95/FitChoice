package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import org.springframework.stereotype.Component;

@Component
public class FitnessClassMapper {

    public FitnessClassDto toDto(FitnessClass fitnessClass){
        if(fitnessClass == null) return null;
        FitnessClassDto dto=new FitnessClassDto();
        dto.setName(fitnessClass.getName());
        dto.setSchedule(fitnessClass.getSchedule());
        dto.setCapacity(fitnessClass.getCapacity());
        dto.setPrice(fitnessClass.getPrice());
        return dto;
    }

    public FitnessClass toEntity(FitnessClassDto dto){
        if (dto == null) return null;
        FitnessClass fitnessClass=new FitnessClass();
        fitnessClass.setName(dto.getName());
        fitnessClass.setSchedule(dto.getSchedule());
        fitnessClass.setCapacity(dto.getCapacity());
        fitnessClass.setPrice(dto.getPrice());
        return fitnessClass;
    }
}
