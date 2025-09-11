package com.zonatech.app.domain.ports.input.evaluaciones;

import com.zonatech.app.domain.models.Evaluacion;

public interface CrearEvaluacionUseCase {
    Evaluacion crearEvaluacion(Evaluacion evaluacion, Long UsuarioId);
}
