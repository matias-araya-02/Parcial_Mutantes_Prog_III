package com.example.mutantes.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stat {

    private long countMutantDna;
    private long countHumanDna;

    // Calcula y retorna la razón entre ADN mutante y humano
    public double getRatio() {
        return countHumanDna == 0 ? 0 : (double) countMutantDna / countHumanDna;
    }
}

