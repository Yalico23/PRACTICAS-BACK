package com.zonatech.app.application.usecases.evaluacion;

import com.zonatech.app.domain.exceptions.EvaluacionNoEncontradaException;
import com.zonatech.app.domain.exceptions.NoPuedeSerNull;
import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.UsuarioRef;
import com.zonatech.app.domain.ports.input.evaluaciones.EditarEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class EditarEvaluacionUseCaseImpl implements EditarEvaluacionUseCase {

    private final EvaluacionesRepositoryPort repositoryPort;
    private final EvaluacionEstudianteRespositoryPort estudianteEvaluacionRepositoryPort;

    @Override
    public Evaluacion update(Evaluacion evaluacion, Long idMentor) {
        // Validación de entrada
        repositoryPort.findById(evaluacion.getId())
                .orElseThrow(() -> new EvaluacionNoEncontradaException("Evaluación no encontrada con ID: " + evaluacion.getId()));
        // No puede ser null
        if (estudianteEvaluacionRepositoryPort.existsByEvaluacionId(evaluacion.getId())) {
            throw new IllegalArgumentException("No se puede editar la evaluación porque ya tiene estudiantes asociados.");
        }

        evaluacion.setMentor(new UsuarioRef(idMentor));
        evaluacion.setFechaCreacion(LocalDate.now());

        return repositoryPort.save(evaluacion);
    }
}
