package com.FitChoice.FitChoice.integrationTest;

import com.FitChoice.FitChoice.model.entity.FitnessClass;
import com.FitChoice.FitChoice.repository.FitnessClassRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FitnessClassRepositoryIntegrationTest {

    @Autowired
    private FitnessClassRepository fitnessClassRepository;

    @Test
    @DisplayName("Should save and find FitnessClass by name ignoring case")
    void testFindFitnessClassByNameIgnoreCase() {

        FitnessClass yogaClass = new FitnessClass();
        yogaClass.setName("Yoga");
        yogaClass.setPrice(49.99);

        fitnessClassRepository.save(yogaClass);


        Optional<FitnessClass> found = fitnessClassRepository.findFitnessClassByNameIgnoreCase("yoga");


        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Yoga");
        assertThat(found.get().getPrice()).isEqualTo(49.99);
    }
}


