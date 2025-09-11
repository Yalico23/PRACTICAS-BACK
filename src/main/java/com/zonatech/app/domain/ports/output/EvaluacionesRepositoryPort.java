package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EvaluacionesRepositoryPort {

    // Debemos de buscar el Mentor para guardar la evaluacion
    Evaluacion save (Evaluacion evaluacion);
    List<Evaluacion> findAllEvaluaciones();
    List<Evaluacion> findAllEvaluacionesById(Long id);
    Optional<Evaluacion> findById(Long id);
    Page<EvaluacionResumen> findEvaluacionesResumenRaw(String emailEstudiante, String filter, Pageable pageable);
    void deleteById(Long id);
    Page<Evaluacion> findByMentorIdWithFilter(String email, String filter, Pageable pageable);
}
