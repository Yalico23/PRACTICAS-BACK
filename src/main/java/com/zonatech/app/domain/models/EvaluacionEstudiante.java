package com.zonatech.app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class EvaluacionEstudiante {
    private Long id;
    private boolean completado;
    private String feedback;
    private int calificacionFinal;
    private LocalDate fechaInicio;
    private UsuarioRef estudiante; // Id del estudiante
    private EvaluacionRef evaluacion; // Id de la evaluación
    private List<RespuestaEstudiante> respuestaEstudiantes;
}
