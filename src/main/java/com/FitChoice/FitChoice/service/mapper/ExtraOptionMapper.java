package com.FitChoice.FitChoice.service.mapper;

import com.FitChoice.FitChoice.model.dto.ExtraOptionDto;
import com.FitChoice.FitChoice.model.entity.ExtraOption;
import org.springframework.stereotype.Component;

@Component
public class ExtraOptionMapper {

    public ExtraOptionDto toDto(ExtraOption extraOption){
        if (extraOption == null) return null;
        ExtraOptionDto dto=new ExtraOptionDto();
        dto.setName(extraOption.getName());
        dto.setPrice(extraOption.getPrice());
        return dto;
    }

    public ExtraOption toEntity(ExtraOptionDto dto){
        if (dto == null) return null;
        ExtraOption extraOption = new ExtraOption();
        extraOption.setName(dto.getName());
        extraOption.setPrice(dto.getPrice());
        return extraOption;
    }
}
