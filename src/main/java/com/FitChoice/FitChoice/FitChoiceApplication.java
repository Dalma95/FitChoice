package com.FitChoice.FitChoice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "FitChoice API", version = "1.0", description = "API that helps users choose a fitness package "))
@SpringBootApplication
public class FitChoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitChoiceApplication.class, args);
	}

}
