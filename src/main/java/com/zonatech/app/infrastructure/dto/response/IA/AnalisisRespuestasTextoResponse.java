package com.zonatech.app.infrastructure.dto.response.IA;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalisisRespuestasTextoResponse {
    private boolean success;
    private AnalisisData data;
    private String message;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AnalisisData {
        private List<AnalisisIA> respuestasAnalizadas;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AnalisisIA {
        private Long preguntaId;
        private Integer notaSugerida;
        private Integer notaMaxima;
        private Double porcentajeAcierto;
        private ComentariosIA comentarios;
        private Boolean requiereRevisionManual;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ComentariosIA {
        private List<String> fortalezas;
        private List<String> debilidades;
        private List<String> sugerencias;
        private String comentarioGeneral;
    }
}
