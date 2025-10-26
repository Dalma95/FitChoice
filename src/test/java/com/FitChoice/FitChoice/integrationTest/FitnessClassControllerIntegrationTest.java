package com.FitChoice.FitChoice.integrationTest;

import com.FitChoice.FitChoice.model.dto.FitnessClassDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FitnessClassControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should create and retrieve FitnessClass via REST API")
    void testCreateAndGetFitnessClass() throws Exception {

        FitnessClassDto dto = new FitnessClassDto();
        dto.setName("Pilates");
        dto.setCapacity(10);
        dto.setPrice(59.99);


        mockMvc.perform(post("/api/fitnessClasses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pilates"))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.price").value(59.99));


        mockMvc.perform(get("/api/fitnessClasses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pilates"))
                .andExpect(jsonPath("$[0].capacity").value(10))
                .andExpect(jsonPath("$[0].price").value(59.99));
    }
}
