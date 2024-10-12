package com.example.mutantes.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseMessage {

    private String message;
    private int mutant;
}

