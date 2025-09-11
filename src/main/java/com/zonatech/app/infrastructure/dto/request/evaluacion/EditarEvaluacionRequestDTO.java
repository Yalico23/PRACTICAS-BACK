package com.zonatech.app.infrastructure.dto.request.evaluacion;

import com.zonatech.app.domain.models.Pregunta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditarEvaluacionRequestDTO {
    @NotNull
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descripcion;
    @NotBlank
    private String tags;
    @NotNull
    private Long mentorId;
    @NotNull
    private List<Pregunta> preguntas;
}
