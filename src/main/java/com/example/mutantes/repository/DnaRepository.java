package com.example.mutantes.repository;

import com.example.mutantes.model.Dna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DnaRepository extends JpaRepository<Dna, Long> {

    Dna findByDna(String dna);

    @Query("SELECT COUNT(d) FROM Dna d WHERE d.isMutant = :type")
    long getAccumulatedByType(@Param("type") int type);
}
