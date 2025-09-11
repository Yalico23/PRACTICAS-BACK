package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionResumen {
    private Long id;
    private String titulo;
    private String descripcion;
    private String mentor;
    private String tecnologia;
    private String estado;
    private String tiempo;
}
