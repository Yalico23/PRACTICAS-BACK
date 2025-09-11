package com.zonatech.app.domain.models;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private Long id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaCreacion;
    private boolean habilitado;
    private String password;
    private String email;
    private String token;
    private boolean mentor;
    private List<Roles> roles;
    private List<Evaluacion> evaluaciones;
    private List<EvaluacionEstudiante> evaluacionEstudiantes;
    private List<Entrevista> entrevistas;
    private List<EntrevistaEstudiante> entrevistaEstudiantes;
}
