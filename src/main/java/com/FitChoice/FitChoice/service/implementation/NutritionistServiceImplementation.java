package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.NutritionistCreateDto;
import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;
import com.FitChoice.FitChoice.repository.NutritionistRepository;
import com.FitChoice.FitChoice.service.interfaceses.NutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutritionistServiceImplementation implements NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Override
    public Nutritionist createNutritionist(Nutritionist nutritionist) {
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    public List<NutritionistDto> findAll() {

        return nutritionistRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NutritionistDto> findNutritionistById(Long id) {
        return nutritionistRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public Nutritionist updateNutritionist(Long id, Nutritionist nutritionist) {
        Nutritionist nutritionistFound = nutritionistRepository.findById(id).orElseThrow(() -> new RuntimeException("Nutritionist not found"));
        nutritionistFound.setName(nutritionist.getName());
        nutritionistFound.setPricePerMonth(nutritionist.getPricePerMonth());
        return nutritionistRepository.save(nutritionistFound);
    }

    @Override
    public void deleteNutritionistById(Long id) {
        nutritionistRepository.deleteById(id);

    }

    @Override
    public Nutritionist toEntity(NutritionistCreateDto dto) {
        Nutritionist nutritionist = new Nutritionist();
        nutritionist.setName(dto.getName());
        nutritionist.setPricePerMonth(dto.getPricePerMonth());
        return nutritionist;
    }

    @Override
    public NutritionistDto toDto(Nutritionist nutritionist) {
        NutritionistDto dto = new NutritionistDto();
        dto.setId(nutritionist.getId());
        dto.setName(nutritionist.getName());
        dto.setPricePerMonth(nutritionist.getPricePerMonth());
        return dto;
    }
}
