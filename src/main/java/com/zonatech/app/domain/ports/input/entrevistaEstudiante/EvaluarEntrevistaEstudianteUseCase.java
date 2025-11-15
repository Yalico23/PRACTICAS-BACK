package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.infrastructure.dto.request.entrevistaestudiante.EntrevistaEstudiantesEntityDto;

public interface EvaluarEntrevistaEstudianteUseCase {
    void evaluarEntrevistaEstudiante(Long idEntrevista, int valoracion, String feedback);
    EntrevistaEstudiante update(EntrevistaEstudiantesEntityDto entrevistaEstudiante);
}
