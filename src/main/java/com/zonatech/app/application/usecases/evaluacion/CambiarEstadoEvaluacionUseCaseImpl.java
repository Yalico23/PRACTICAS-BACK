package com.zonatech.app.application.usecases.evaluacion;

import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.ports.input.evaluaciones.CambiarEstadoEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CambiarEstadoEvaluacionUseCaseImpl implements CambiarEstadoEvaluacionUseCase {
    
private final EvaluacionesRepositoryPort repositoryPort;

    @Override
    public Evaluacion cambiarEstadoEvaluacion(Long idEvaluacion, boolean estadoPrevio) {
        Evaluacion evaluacion = repositoryPort.findById(idEvaluacion)
                .orElseThrow( () -> new EvaluacionNoEncontradaException("Evaluacion no ecnontrada con id :" + idEvaluacion));

        evaluacion.setActivo(!estadoPrevio);
        return repositoryPort.save(evaluacion);
    }
}
