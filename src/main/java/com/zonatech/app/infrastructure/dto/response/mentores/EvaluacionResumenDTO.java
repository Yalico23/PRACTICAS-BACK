package com.zonatech.app.infrastructure.dto.response.mentores;

public record EvaluacionResumenDTO(
        Long idEvaluacion,
        String titulo,
        String nombreMentor,
        String apellidoMentor,
        String tags,
        boolean estado,
        int tiempoTotal
) {
}
