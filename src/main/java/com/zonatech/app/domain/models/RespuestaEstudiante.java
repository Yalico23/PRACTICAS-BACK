package com.zonatech.app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class RespuestaEstudiante {
    private Long id;
    private boolean esCorrecta;
    private int nota;
    private String respuesta;

    private PreguntasRef pregunta;
    private OpcionRespuestaRef opcionRespuesta;
    private EvaluacionEstudianteRef evaluacionEstudiante;
}
