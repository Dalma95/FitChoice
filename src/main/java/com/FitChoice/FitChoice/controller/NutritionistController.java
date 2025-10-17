package com.FitChoice.FitChoice.controller;

import com.FitChoice.FitChoice.model.dto.NutritionistDto;
import com.FitChoice.FitChoice.model.entity.Nutritionist;
import com.FitChoice.FitChoice.repository.NutritionistRepository;
import com.FitChoice.FitChoice.service.interfaceses.NutritionistService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nutritionists")
public class NutritionistController {

    @Autowired
    NutritionistService nutritionistService;

    @Operation(summary = "Create nutritionist")
    @PostMapping
    public ResponseEntity<Nutritionist> createNutritionist(@RequestBody NutritionistDto dto){
        return ResponseEntity.ok(nutritionistService.createNutritionist(nutritionistService.toEntity(dto)));
    }

    @Operation(summary = "Find all nutritionists")
    @GetMapping
    public ResponseEntity<List<Nutritionist>> findAllNutritionists(){
        return ResponseEntity.ok(nutritionistService.findAll());
    }

    @Operation(summary = "Find nutritionist by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Nutritionist>> findNutritionistById(@PathVariable Long id){
        return ResponseEntity.ok(nutritionistService.findNutritionistById(id));
    }

    @Operation(summary = "Delete nutritionist by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutritionistById(@PathVariable Long id){
        nutritionistService.deleteNutritionistById(id);
        return ResponseEntity.noContent().build();
    }
}
