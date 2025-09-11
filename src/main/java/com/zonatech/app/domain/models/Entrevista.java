package com.zonatech.app.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entrevista {
    private Long id;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private boolean activo;
    private UsuarioRef mentor;
    List<PreguntaEntrevista> preguntas;
    List<EntrevistaEstudiante> entrevistasEstudiantes;
}
