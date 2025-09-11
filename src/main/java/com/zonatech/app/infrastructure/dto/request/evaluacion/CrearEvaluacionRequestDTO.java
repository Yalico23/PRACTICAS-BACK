package com.zonatech.app.infrastructure.dto.request.evaluacion;

import com.zonatech.app.domain.models.Pregunta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CrearEvaluacionRequestDTO(
@NotBlank String titulo,
@NotBlank String descripcion,
@NotBlank String tags,
@NotNull Long mentorId,
@NotNull List<Pregunta> preguntas
) {
}
