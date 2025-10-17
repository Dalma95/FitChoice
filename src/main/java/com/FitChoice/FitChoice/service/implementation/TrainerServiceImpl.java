package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.TrainerDto;
import com.FitChoice.FitChoice.model.entity.Trainer;
import com.FitChoice.FitChoice.repository.TrainerRepository;
import com.FitChoice.FitChoice.service.interfaceses.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Optional<Trainer> findTrainerById(Long id) {
        return trainerRepository.findById(id);
    }

    @Override
    public void deleteTrainerById(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer toEntity(TrainerDto dto) {
        Trainer trainer = new Trainer();
        trainer.setName(dto.getName());
        trainer.setPricePerMonth(dto.getPricePerMonth());
        return trainer;
    }

    @Override
    public TrainerDto toDto(Trainer trainer) {
        TrainerDto dto = new TrainerDto();
        dto.setName(trainer.getName());
        dto.setPricePerMonth(trainer.getPricePerMonth());
        return dto;
    }
}
