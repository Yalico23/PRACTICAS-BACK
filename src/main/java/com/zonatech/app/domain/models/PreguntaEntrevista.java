package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreguntaEntrevista {
    private Long id;
    private String pregunta;
    private int tiempo;
    private int valor;
    private EntrevistaRef entrevista;
}
