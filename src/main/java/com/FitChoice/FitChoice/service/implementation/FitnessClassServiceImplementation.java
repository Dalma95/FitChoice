package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.FitnessClassCreateDto;
import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.repository.FitnessClassRepository;
import com.FitChoice.FitChoice.service.interfaceses.FitnessClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FitnessClassServiceImplementation implements FitnessClassService {

    @Autowired
    FitnessClassRepository fitnessClassRepository;

    @Override
    public FitnessClass createFitnessClass(FitnessClass fitnessClass) {
        return fitnessClassRepository.save(fitnessClass);
    }

    @Override
    public List<FitnessClassDto> findAllFitnessClasses() {
        return fitnessClassRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FitnessClassDto> findClassById(Long id) {
        return fitnessClassRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public FitnessClass updateFitnessClass(Long id, FitnessClass fitnessClass) {
        FitnessClass fitnessClassFound = fitnessClassRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found" + id));
        fitnessClassFound.setName(fitnessClass.getName());
        fitnessClassFound.setPrice(fitnessClass.getPrice());
        return fitnessClassRepository.save(fitnessClassFound);
    }

    @Override
    public void deleteClassById(Long id) {
        fitnessClassRepository.deleteById(id);

    }

    public FitnessClassDto toDto(FitnessClass fitnessClass){
        FitnessClassDto dto = new FitnessClassDto();
        dto.setId(fitnessClass.getId());
        dto.setName(fitnessClass.getName());
        dto.setPrice(fitnessClass.getPrice());
        return dto;
    }

    public FitnessClass toEntity(FitnessClassCreateDto dto){
        FitnessClass fitnessClass = new FitnessClass();
        fitnessClass.setName(dto.getName());
        fitnessClass.setPrice(dto.getPrice());
        return fitnessClass;
    }


}
