package com.zonatech.app.domain.models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnalisisRespuestasTextoRequest {
    private Long evaluacionId;
    private List<RespuestaTextoIA> respuestasTexto;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RespuestaTextoIA {
        private Long preguntaId;
        private String pregunta;
        private String respuestaEstudiante;
        private Integer valorMaximo;
        private String criteriosEvaluacion;
    }
}
