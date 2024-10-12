package com.example.mutantes.service;

import com.example.mutantes.model.Dna;
import com.example.mutantes.repository.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MutantServiceTest {

    @Mock
    private DnaRepository dnaRepository;

    @InjectMocks
    private MutantService mutantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
    }

    @Test
    public void testIsMutant() {
        String[] dna = { "AAAA", "AAAA", "AAAA", "AAAA" };

        when(dnaRepository.findByDna(any(String.class))).thenReturn(null); // No existe en la base de datos
        when(dnaRepository.saveAndFlush(any(Dna.class))).thenAnswer(invocation -> {
            Dna savedDna = invocation.getArgument(0);
            savedDna.setId(1L);  // Simular un ID generado
            return savedDna;
        });

        Mono<Boolean> result = mutantService.isMutant(dna);
        Boolean isMutant = result.block();  // Bloquea y obtiene el resultado
        assertNotNull(isMutant);  // Asegúrate de que no es null
        assertTrue(isMutant);  // Verifica que sea verdadero
        verify(dnaRepository).saveAndFlush(any(Dna.class));
    }

    // Tests cubriendo todas las secuencias de matriz 6x6
    @Test
    public void testRows() {
        String[] dna = {
                "AAAATG",
                "TGCAGT",
                "GCTTCC",
                "CCCCTG",
                "GTAGTC",
                "AGTCAC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testColumns() {
        String[] dna = {
                "AGAATG",
                "TGCAGT",
                "GCTTCC",
                "GTCCTC",
                "GTAGTC",
                "GGTCAC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testMainDiagonals() {
        String[] dna = {
                "AGAATG",
                "TACAGT",
                "GCAGCC",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testSecondaryLeftDiagonals() {
        String[] dna = {
                "ATAATG",
                "GTTAGT",
                "GGCTCG",
                "TTGATG",
                "GTAGTC",
                "AGTCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testSecondaryRightDiagonals() {
        String[] dna = {
                "ATAATG",
                "GTATGA",
                "GCTTAG",
                "TTTAGG",
                "GTAGTC",
                "AGTCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testTertiaryLeftDiagonals() {
        String[] dna = {
                "ATGATG",
                "GTAGTA",
                "CCTTGG",
                "TCTAGG",
                "GGCGTC",
                "AGTCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testTertiaryRightDiagonals() {
        String[] dna = {
                "ATGATG",
                "GTATTA",
                "AATTGG",
                "ACTAGT",
                "GGAGTC",
                "AGGCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testNonMutant() {
        String[] dna = {
                "ATGATG",
                "GTCTTA",
                "AATTGG",
                "ACTAGT",
                "GGATTC",
                "AGGCAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertFalse(isMutant);
    }

    // Tests brindados por el profesor
    @Test
    public void testMutant1() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testNonMutant1() {
        String[] dna = {
                "AAAT",
                "AACC",
                "AAAC",
                "CGGG"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertFalse(isMutant);
    }

    @Test
    public void testMutant2() {
        String[] dna = {
                "TGAC",
                "AGCC",
                "TGAC",
                "GGTC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testMutant3() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testNonMutant2() {
        String[] dna = {
                "TGAC",
                "ATCC",
                "TAAG",
                "GGTC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertFalse(isMutant);
    }

    @Test
    public void testMutant4() {
        String[] dna = {
                "TCGGGTGAT",
                "TGATCCTTT",
                "TACGAGTGA",
                "AAATGTACG",
                "ACGAGTGCT",
                "AGACACATG",
                "GAATTCCAA",
                "ACTACGACC",
                "TGAGTATCC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }

    @Test
    public void testMutant5() {
        String[] dna = {
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "TTTTTTTTT",
                "CCGACCAGT",
                "GGCACTCCA",
                "AGGACACTA",
                "CAAAGGCAT",
                "GCAGTCCCC"
        };
        Boolean isMutant = mutantService.isMutant(dna).block();
        assertNotNull(isMutant);
        assertTrue(isMutant);
    }
}
