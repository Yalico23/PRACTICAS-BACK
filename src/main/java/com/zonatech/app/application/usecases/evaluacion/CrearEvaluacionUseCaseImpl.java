package com.zonatech.app.application.usecases.evaluacion;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.UsuarioRef;
import com.zonatech.app.domain.ports.input.evaluaciones.CrearEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CrearEvaluacionUseCaseImpl implements CrearEvaluacionUseCase {

    private final EvaluacionesRepositoryPort repositoryPort;

    @Override
    public Evaluacion crearEvaluacion(Evaluacion evaluacion, Long idMentor) {
        evaluacion.setMentor(new UsuarioRef(idMentor));
        evaluacion.setFechaCreacion(LocalDate.now());
        return repositoryPort.save(evaluacion);
    }
}
