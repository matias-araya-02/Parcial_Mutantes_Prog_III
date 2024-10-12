package com.example.mutantes.controller;

import com.example.mutantes.model.Payload;
import com.example.mutantes.service.MutantService;
import com.example.mutantes.service.StatisticsService;
import com.example.mutantes.model.Stat;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/mutant")
@RequiredArgsConstructor
public class MutantController {

    private static final Logger log = LoggerFactory.getLogger(MutantController.class);

    private final MutantService mutantService;
    private final StatisticsService statisticsService;

    // Verifica si el ADN proporcionado corresponde a un mutante o a un humano.
    @PostMapping
    public Mono<ResponseEntity<ResponseMessage>> isMutant(@Valid @RequestBody Payload payload) {
        return mutantService.isMutant(payload.getDna())
                .flatMap(isMutant -> {
                    ResponseMessage responseMessage;
                    if (isMutant) {
                        log.info("ADN mutante detectado");
                        responseMessage = new ResponseMessage("Mutante \uD83D\uDC7D✌", 1); // 1 para mutante
                        return Mono.just(new ResponseEntity<>(responseMessage, HttpStatus.OK));
                    } else {
                        log.info("ADN humano detectado");
                        responseMessage = new ResponseMessage("Humano \uD83D\uDE09\uD83D\uDC4D", 0); // 0 para humano
                        return Mono.just(new ResponseEntity<>(responseMessage, HttpStatus.FORBIDDEN));
                    }
                })
                .switchIfEmpty(Mono.just(new ResponseEntity<>(new ResponseMessage("El ADN proporcionado no es válido", -1), HttpStatus.BAD_REQUEST))); //-1 para ADN no valido
    }

    // Recupera las estadísticas de ADN mutante y humano.
    @GetMapping("/stats")
    public Mono<Stat> getStats() {
        log.info("Recuperando estadísticas de ADN mutante y humano");
        return statisticsService.getStats();
    }
}
