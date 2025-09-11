package com.zonatech.app.domain.ports.input.evaluaciones;

import com.zonatech.app.domain.models.Evaluacion;

public interface EditarEvaluacionUseCase {
    Evaluacion update (Evaluacion evaluacion, Long usuarioId);
}
