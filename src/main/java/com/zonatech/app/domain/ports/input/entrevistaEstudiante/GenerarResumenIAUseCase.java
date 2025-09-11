package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

import com.zonatech.app.domain.models.ResponseEntrevistaEstudianteIA;

public interface GenerarResumenIAUseCase {
    ResponseEntrevistaEstudianteIA generarResumenIA(String resumen);
}
