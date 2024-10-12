package com.example.mutantes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantApplication {

    private static final Logger log = LoggerFactory.getLogger(MutantApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MutantApplication.class, args);
        log.info("\n\n -------------- CORRIENDO ADN HUMANUTANTE --------------");
    }
}
