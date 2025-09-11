package com.zonatech.app.application.usecases.evaluacionEstudiante;

import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.ListEvaluacionesEstudianteByIdEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListEvaluacionesEstudianteByIdEvaluacionUseCaseImpl
        implements ListEvaluacionesEstudianteByIdEvaluacionUseCase {

    private final EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort;

    @Override
    public List<Object[]> listEvaluacionesEstudianteByIdEvaluacion(Long idEvaluacion) {

        if(idEvaluacion == null){
            throw new EvaluacionNoEncontradaException("El ID de la evaluación no puede ser nulo.");
        }
        return evaluacionEstudianteRespositoryPort.listEvaluacionesPendientesByEvaluacionId(idEvaluacion);
    }

    @Override
    public EvaluacionEstudiante findById(Long id) {
        return evaluacionEstudianteRespositoryPort.findById(id)
                .orElseThrow(() -> new EvaluacionNoEncontradaException("Evaluación no encontrada con ID: " + id));
    }
}
