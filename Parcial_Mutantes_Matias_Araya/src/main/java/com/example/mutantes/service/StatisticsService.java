package com.example.mutantes.service;

import com.example.mutantes.model.Stat;
import com.example.mutantes.repository.DnaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private static final Logger log = LoggerFactory.getLogger(StatisticsService.class);
    private final DnaRepository dnaRepository;

    // Recupera y calcula estadísticas de ADN mutante y humano
    public Mono<Stat> getStats() {
        try {
            long mutantCount = dnaRepository.getAccumulatedByType(1);
            long humanCount = dnaRepository.getAccumulatedByType(0);
            Stat stat = new Stat(mutantCount, humanCount);
            log.info("Estadísticas calculadas correctamente: Ratio = {}", stat.getRatio());
            return Mono.just(stat);
        } catch (Exception e) {
            log.error("Error al recuperar estadísticas de la base de datos", e);
            return Mono.error(new RuntimeException("Error al calcular estadísticas"));
        }
    }
}
