package com.example.mutantes.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
public class Dna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ADN", unique = true)
    private String dna;

    @Column(name = "ES_MUTANTE")
    private int isMutant;

    @Column(name = "ACUMULAR")
    private long accumulate;
}

