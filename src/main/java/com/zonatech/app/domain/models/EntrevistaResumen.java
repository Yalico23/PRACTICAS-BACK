package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntrevistaResumen {
    private Long id;
    private String nombre;
    private String descripcion;
    private String mentor;
    private String estado;
    private String tiempo;
    private String feedBack;
}
