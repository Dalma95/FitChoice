package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.PersonalTrainerDto;
import com.FitChoice.FitChoice.model.entity.PersonalTrainer;
import org.springframework.stereotype.Component;

@Component
public class PersonalTrainerMapper {

    public PersonalTrainerDto toDto(PersonalTrainer trainer){
        PersonalTrainerDto dto = new PersonalTrainerDto();
        dto.setName(trainer.getName());
        dto.setSpecialization(trainer.getSpecialization());
        dto.setPhoneNumber(trainer.getPhoneNumber());
        dto.setPricePerMonth(trainer.getPricePerMonth());
        return dto;
    }

    public PersonalTrainer toEntity(PersonalTrainerDto dto){
        PersonalTrainer trainer = new PersonalTrainer();
        trainer.setName(dto.getName());
        trainer.setSpecialization(dto.getSpecialization());
        trainer.setPhoneNumber(dto.getPhoneNumber());
        trainer.setPricePerMonth(dto.getPricePerMonth());
        return trainer;
    }
}
