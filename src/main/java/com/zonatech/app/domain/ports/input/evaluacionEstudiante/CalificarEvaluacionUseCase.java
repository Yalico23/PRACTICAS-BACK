package com.zonatech.app.domain.ports.input.evaluacionEstudiante;

import com.zonatech.app.domain.models.AnalisisRespuestasTextoRequest;
import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.DtoRequestEvaluarEvaluacion;
import com.zonatech.app.infrastructure.dto.response.IA.AnalisisRespuestasTextoResponse;

public interface CalificarEvaluacionUseCase {
    AnalisisRespuestasTextoResponse analizarRespuestasTexto(AnalisisRespuestasTextoRequest request);
    EvaluacionEstudiante calificarEvaluacion (DtoRequestEvaluarEvaluacion dtoRequestEvaluarEvaluacion);
}
