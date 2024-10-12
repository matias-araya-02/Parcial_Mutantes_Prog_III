package com.example.mutantes.service;

import com.example.mutantes.model.Dna;
import com.example.mutantes.repository.DnaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MutantService {

    private static final Logger log = LoggerFactory.getLogger(MutantService.class);
    private static final int IS_MUTANT = 1;  // Constante para ADN mutante
    private static final int IS_HUMAN = 0;   // Constante para ADN humano
    private static final int SEQUENCE_LENGTH = 4;  // Longitud de secuencia para determinar mutante

    private final DnaRepository dnaRepository;

    // Verifica si el ADN es mutante
    public Mono<Boolean> isMutant(String[] dna) {
        if (!isValidDna(dna)) {
            log.error("El ADN proporcionado no es válido");
            return Mono.empty();  // Devuelve vacío si el ADN no es válido
        }

        String dnaString = Arrays.toString(dna);
        Dna existingDna = dnaRepository.findByDna(dnaString);  // Busca ADN existente
        if (existingDna != null) {
            log.info("ADN previamente analizado. Actualizando registros.");
            updateDna(existingDna);  // Actualiza el registro si ya existe
            return Mono.just(existingDna.getIsMutant() == IS_MUTANT);
        }

        boolean isMutant = findMutantSequences(dna);  // Verifica secuencias mutantes
        saveDna(dnaString, isMutant ? IS_MUTANT : IS_HUMAN);  // Guarda el ADN en la base de datos
        return Mono.just(isMutant);
    }

    // Busca secuencias mutantes en el ADN
    private boolean findMutantSequences(String[] dna) {
        if (dna.length < SEQUENCE_LENGTH || dna[0].length() < SEQUENCE_LENGTH) {
            return false;  // Retorna falso si el tamaño es insuficiente
        }

        int sequenceCount = 0;

        sequenceCount += countHorizontal(dna);  // Cuenta secuencias horizontales
        if (sequenceCount > 1) return true;  // Devuelve verdadero si hay más de 1 secuencia

        sequenceCount += countVertical(dna);  // Cuenta secuencias verticales
        if (sequenceCount > 1) return true;  // Devuelve verdadero si hay más de 1 secuencia

        sequenceCount += countDiagonal(dna);  // Cuenta secuencias diagonales
        return sequenceCount > 1;  // Devuelve verdadero si hay más de 1 secuencia
    }

    // Valida el ADN proporcionado
    private boolean isValidDna(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;  // Retorna falso si el ADN es nulo o vacío
        }

        int size = dna.length;
        Pattern pattern = Pattern.compile("[ATCG]+");  // Expresión regular para ADN

        for (String s : dna) {
            if (s == null || s.trim().isEmpty() || s.length() != size || !pattern.matcher(s).matches()) {
                return false;  // Retorna falso si hay secuencias inválidas (null, espacios vacios, que no sea NxN, que tenga caracteres invalidos)
            }
        }
        return true;  // Retorna verdadero si todas las secuencias son válidas
    }

    // Guarda el ADN en la base de datos
    private void saveDna(String dna, int type) {
        Dna newDna = new Dna();
        newDna.setDna(dna);
        newDna.setIsMutant(type);
        newDna.setAccumulate(1L);
        try {
            dnaRepository.saveAndFlush(newDna);
            log.info("ADN guardado exitosamente");
        } catch (Exception e) {
            log.error("Error al guardar el ADN en la base de datos", e);
        }
    }

    // Actualiza el conteo de ADN en la base de datos
    private void updateDna(Dna dna) {
        dna.setAccumulate(dna.getAccumulate() + 1);  // Incrementa el acumulado
        try {
            dnaRepository.saveAndFlush(dna);
            log.info("ADN actualizado correctamente");
        } catch (Exception e) {
            log.error("Error al actualizar el ADN en la base de datos", e);
        }
    }

    // Cuenta secuencias horizontales
    private int countHorizontal(String[] dna) {
        int count = 0;
        for (String row : dna) {
            for (int i = 0; i <= row.length() - SEQUENCE_LENGTH; i++) {
                char current = row.charAt(i);
                boolean isMutantSequence = true;
                for (int j = 1; j < SEQUENCE_LENGTH; j++) {
                    if (row.charAt(i + j) != current) {
                        isMutantSequence = false;
                        break;
                    }
                }
                if (isMutantSequence) count++;  // Incrementa el conteo si se encuentra una secuencia
            }
        }
        return count;  // Devuelve el conteo de secuencias
    }

    // Cuenta secuencias verticales
    private int countVertical(String[] dna) {
        int count = 0;
        int size = dna.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - SEQUENCE_LENGTH; j++) {
                char current = dna[j].charAt(i);
                boolean isMutantSequence = true;
                for (int k = 1; k < SEQUENCE_LENGTH; k++) {
                    if (dna[j + k].charAt(i) != current) {
                        isMutantSequence = false;
                        break;
                    }
                }
                if (isMutantSequence) count++;  // Incrementa el conteo si se encuentra una secuencia
            }
        }
        return count;  // Devuelve el conteo de secuencias
    }

    // Cuenta secuencias diagonales
    private int countDiagonal(String[] dna) {
        int count = 0;
        int size = dna.length;

        // Cuenta diagonales de izquierda a derecha
        for (int i = 0; i <= size - SEQUENCE_LENGTH; i++) {
            for (int j = 0; j <= size - SEQUENCE_LENGTH; j++) {
                char current = dna[i].charAt(j);
                boolean isMutantSequence = true;
                for (int k = 1; k < SEQUENCE_LENGTH; k++) {
                    if (dna[i + k].charAt(j + k) != current) {
                        isMutantSequence = false;
                        break;
                    }
                }
                if (isMutantSequence) count++;  // Incrementa el conteo si se encuentra una secuencia
            }
        }

        // Cuenta diagonales de derecha a izquierda
        for (int i = 0; i <= size - SEQUENCE_LENGTH; i++) {
            for (int j = SEQUENCE_LENGTH - 1; j < size; j++) {
                char current = dna[i].charAt(j);
                boolean isMutantSequence = true;
                for (int k = 1; k < SEQUENCE_LENGTH; k++) {
                    if (dna[i + k].charAt(j - k) != current) {
                        isMutantSequence = false;
                        break;
                    }
                }
                if (isMutantSequence) count++;  // Incrementa el conteo si se encuentra una secuencia
            }
        }

        return count;  // Devuelve el conteo de secuencias
    }
}
