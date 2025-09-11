package com.zonatech.app.infrastructure.dto.response.mentores;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListEvaluacionesByMentoresDtoResponse {
    private Long id;
    private String titulo;
    private String descripcion;
    private boolean activo;
}
