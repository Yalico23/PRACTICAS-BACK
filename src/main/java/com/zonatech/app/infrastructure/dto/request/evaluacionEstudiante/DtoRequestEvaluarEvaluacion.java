package com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRequestEvaluarEvaluacion {
    private Long idEvaluacion;
    private String feedback;
    private Integer notaFinal;
    private List<DtoNotaPregunta> notasPreguntas;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DtoNotaPregunta {
        private Long idPregunta;
        private Integer notaPregunta;
    }
}
