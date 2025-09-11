package com.zonatech.app.domain.ports.input.evaluacionEstudiante;

import com.zonatech.app.domain.models.EvaluacionEstudiante;

public interface ResponserEvaluacionUseCase {
    EvaluacionEstudiante responderEvaluacion (EvaluacionEstudiante evaluacionEstudiante);
    void EvaluacionRespondida (Long idEvaluacion, Long idEstudiante);
}
