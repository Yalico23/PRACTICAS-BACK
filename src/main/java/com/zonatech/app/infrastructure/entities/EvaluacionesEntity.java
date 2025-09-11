package com.zonatech.app.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "evaluaciones")
public class EvaluacionesEntity {

    // nota : Atributos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descripcion;
    private String tags;
    private boolean activo;
    private LocalDate fechaCreacion;

    // nota : relación con la tabla usuario (mentor)
    @JsonBackReference("usuario-evaluaciones")
    @ManyToOne
    @JoinColumn(name = "idMentor")
    private UsuarioEntity mentor;

    // nota : relación con la tabla evaluacionEstudiante
    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluacionEstudianteEntity> evaluacionEstudiantes;

    // nota : relación con la tabla preguntas
    @JsonManagedReference("evaluacion-preguntas")
    @OneToMany(mappedBy = "evaluacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaEntity> preguntas;

}
