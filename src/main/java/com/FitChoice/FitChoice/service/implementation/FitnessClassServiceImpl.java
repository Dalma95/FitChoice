package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.repository.FitnessClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FitnessClassServiceImpl implements FitnessClassService {

    @Autowired
    FitnessClassRepository fitnessClassRepo;

    @Override
    public FitnessClass createFitnessClass(FitnessClassDto fitnessClassDto) {
        FitnessClass fitnessClass = new FitnessClass();
        fitnessClass.setName(fitnessClassDto.getName());
        fitnessClass.setCapacity(fitnessClassDto.getCapacity());
        fitnessClass.setPrice(fitnessClassDto.getPrice());
        return fitnessClassRepo.save(fitnessClass);
    }

    @Override
    public List<FitnessClass> findAllFitnessClasses() {
        return fitnessClassRepo.findAll();
    }

    @Override
    public Optional<FitnessClass> findClassById(Long id) {
        return fitnessClassRepo.findById(id);
    }

    @Override
    public FitnessClass updateFitnessClass(Long id, FitnessClass fitnessClass) {
        FitnessClass fitnessClassFound = fitnessClassRepo.findById(id).orElseThrow(() -> new RuntimeException("Class not found" + id));
        fitnessClassFound.setName(fitnessClass.getName());
        fitnessClassFound.setCapacity(fitnessClass.getCapacity());
        fitnessClassFound.setPrice(fitnessClass.getPrice());
        return fitnessClassRepo.save(fitnessClass);
    }

    @Override
    public void deleteClassById(Long id) {
        fitnessClassRepo.deleteById(id);

    }


}
