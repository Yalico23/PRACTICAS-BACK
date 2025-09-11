package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntrevistaEstudianteIA {
    private String analisis;
    private int notaSugerida;
    private List<String> fortalezas;
    private List<String> debilidades;
    private List<String> recomendaciones;
}
