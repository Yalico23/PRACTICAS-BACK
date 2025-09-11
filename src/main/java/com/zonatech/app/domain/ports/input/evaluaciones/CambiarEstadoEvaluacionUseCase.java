package com.zonatech.app.domain.ports.input.evaluaciones;

import com.zonatech.app.domain.models.Evaluacion;

public interface CambiarEstadoEvaluacionUseCase {
    Evaluacion cambiarEstadoEvaluacion(Long idEvaluacion, boolean estadoPrevio);
}
