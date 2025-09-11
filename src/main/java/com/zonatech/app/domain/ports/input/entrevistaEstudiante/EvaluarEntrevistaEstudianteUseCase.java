package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

public interface EvaluarEntrevistaEstudianteUseCase {
    void evaluarEntrevistaEstudiante(Long idEntrevista, int valoracion, String feedback);
}
