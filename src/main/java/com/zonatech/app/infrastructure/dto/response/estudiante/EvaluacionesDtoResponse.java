package com.zonatech.app.infrastructure.dto.response.estudiante;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluacionesDtoResponse{
    private Long id;
    private String titulo;
    private String descripcion;
    private String mentor;
    private String tecnologia;
    private String estado;
    private String tiempo;
}
