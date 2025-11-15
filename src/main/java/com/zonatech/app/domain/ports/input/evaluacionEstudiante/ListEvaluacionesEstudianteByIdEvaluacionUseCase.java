package com.zonatech.app.domain.ports.input.evaluacionEstudiante;

import com.zonatech.app.domain.models.EvaluacionEstudiante;

import java.util.List;

public interface ListEvaluacionesEstudianteByIdEvaluacionUseCase {
    List<Object[]> listEvaluacionesEstudianteByIdEvaluacion(Long idEvaluacion);
    EvaluacionEstudiante findById(Long id);
    EvaluacionEstudiante findIdEvaluacionEstudiante(Long idEstudiante, Long idEvaluacion);
}