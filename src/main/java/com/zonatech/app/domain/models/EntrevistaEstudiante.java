package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntrevistaEstudiante {
    private Long id;
    private String feedBack;
    private boolean completado;
    private int valoracion;
    private String URLVideo;
    private String videoResumen;
    private LocalDate fechaEntrevista;
    private EntrevistaRef entrevista;
    private UsuarioRef estudiante;
}
