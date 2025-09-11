package com.zonatech.app.domain.models;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {
    private Long id;
    private String pregunta;
    private String tipoPregunta;
    private int tiempo;
    private int valor;
    private List<OpcionRespuesta> opcionRespuestas;
}
