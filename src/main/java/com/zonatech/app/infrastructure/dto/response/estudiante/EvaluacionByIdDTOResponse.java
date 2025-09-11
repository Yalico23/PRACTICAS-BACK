package com.zonatech.app.infrastructure.dto.response.estudiante;

import com.zonatech.app.domain.models.Pregunta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionByIdDTOResponse {
    private Long id;
    private String titulo;
    private List<Pregunta> preguntas;

}
