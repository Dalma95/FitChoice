package com.FitChoice.FitChoice.service.implementation;

import com.FitChoice.FitChoice.model.dto.TrainerDto;
import com.FitChoice.FitChoice.model.entity.Trainer;
import com.FitChoice.FitChoice.repository.TrainerRepository;
import com.FitChoice.FitChoice.service.interfaceses.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImplementation implements TrainerService {

    @Autowired
    TrainerRepository trainerRepository;

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    @Override
    public List<TrainerDto> findAllTrainers() {
        return trainerRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TrainerDto> findTrainerById(Long id) {
        return trainerRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public void deleteTrainerById(Long id) {
        trainerRepository.deleteById(id);
    }

    @Override
    public Trainer updateTrainer(Long id, Trainer trainer) {
        Trainer trainerFound = trainerRepository.findById(id).orElseThrow(() -> new RuntimeException("Trainer not found"));
        trainerFound.setName(trainer.getName());
        trainerFound.setPricePerMonth(trainer.getPricePerMonth());
        return trainerRepository.save(trainerFound);
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
