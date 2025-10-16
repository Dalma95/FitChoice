package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;
import com.FitChoice.FitChoice.repository.NutritionistRepository;
import com.FitChoice.FitChoice.service.interfaceses.NutritionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionistServiceImpl implements NutritionistService {

    @Autowired
    NutritionistRepository nutritionistRepository;

    @Override
    public Nutritionist createNutritionist(Nutritionist nutritionist) {
        return nutritionistRepository.save(nutritionist);
    }

    @Override
    public List<Nutritionist> findAll() {
        return nutritionistRepository.findAll();
    }

    @Override
    public Optional<Nutritionist> findNutritionistById(Long id) {
        return nutritionistRepository.findById(id);
    }

    @Override
    public void deleteNutritionistById(Long id) {
        nutritionistRepository.deleteById(id);

    }

    @Override
    public Nutritionist toEntity(NutritionistDto dto) {
        Nutritionist nutritionist = new Nutritionist();
        nutritionist.setName(dto.getName());
        nutritionist.setPricePerMonth(dto.getPricePerMonth());
        return nutritionist;
    }
}
