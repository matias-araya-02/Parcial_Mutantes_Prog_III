package com.example.mutantes.config;

import com.example.mutantes.repository.DnaRepository;
import com.example.mutantes.service.MutantService;
import com.example.mutantes.service.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // Crea un bean de MutantService y lo inicializa con el repositorio de ADN proporcionado.
    @Bean
    public MutantService mutantService(DnaRepository dnaRepository) {
        return new MutantService(dnaRepository);
    }
    // Crea un bean de StatisticsService y lo inicializa con el repositorio de ADN proporcionado.
    @Bean
    public StatisticsService statisticsService(DnaRepository dnaRepository) {
        return new StatisticsService(dnaRepository);
    }
}