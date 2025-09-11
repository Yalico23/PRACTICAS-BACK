package com.zonatech.app.infrastructure.dto.response.mentores;

public record CreateEvaluacionDtoResponse(
        String titulo,
        String descripcion,
        boolean activo,
        String fechaCreacion
) {
}
