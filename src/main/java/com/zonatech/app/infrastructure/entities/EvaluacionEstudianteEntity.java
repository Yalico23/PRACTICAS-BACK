package com.zonatech.app.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluacionEstudiante")
public class EvaluacionEstudianteEntity {

    // nota : atributos de la tabla evaluacionEstudiante
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean completado;
    private LocalDate fechaInicio;
    private String feedback;
    private int calificacionFinal;

    // nota : relación con la tabla evaluaciones
    @ManyToOne
    @JoinColumn(name = "idEvaluacion")
    private EvaluacionesEntity evaluacion;

    // nota : relación con la tabla usuarios (estudiantes)
    @ManyToOne
    @JoinColumn(name = "idEstudiante")
    private UsuarioEntity estudiante;

    // nota : relación con la tabla respuestaEstudiante
    @OneToMany(mappedBy = "evaluacionEstudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RespuestaEstudianteEntity> respuestaEstudiantes;
}
