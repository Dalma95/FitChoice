package com.FitChoice.FitChoice.repository;

import com.FitChoice.FitChoice.model.entity.ExtraOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtraOptionRepository extends JpaRepository<ExtraOption,Long> {
    Optional<ExtraOption> findByNameIgnoreCase(String name);

}
