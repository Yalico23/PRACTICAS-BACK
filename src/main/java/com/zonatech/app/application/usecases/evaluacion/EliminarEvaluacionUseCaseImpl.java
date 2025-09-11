package com.zonatech.app.application.usecases.evaluacion;

import com.zonatech.app.domain.ports.input.evaluaciones.EliminarEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EliminarEvaluacionUseCaseImpl implements EliminarEvaluacionUseCase {

    private final EvaluacionesRepositoryPort respositoryPort;
    private final EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort;

    @Override
    public void eliminarEvaluacion(Long idEvaluacion) {

        if (evaluacionEstudianteRespositoryPort.existsByEvaluacionId(idEvaluacion)) {
            throw new IllegalArgumentException("No se puede eliminar la evaluación porque tiene estudiantes asociados.");
        }
        respositoryPort.deleteById(idEvaluacion);
    }
}
