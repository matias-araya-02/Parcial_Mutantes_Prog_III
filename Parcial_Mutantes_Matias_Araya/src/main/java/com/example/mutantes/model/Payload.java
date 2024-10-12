package com.example.mutantes.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Payload {

    @NotEmpty(message = "El ADN no puede estar vacío")
    private String[] dna;
}
