package com.zonatech.app.domain.models;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Evaluacion {
    private Long id;
    private String titulo;
    private String descripcion;
    private String tags;
    private boolean activo;
    private LocalDate fechaCreacion;
    private List<Pregunta> preguntas;
    private UsuarioRef mentor; // nota : excluir
}
