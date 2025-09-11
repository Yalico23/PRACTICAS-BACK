package com.zonatech.app.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preguntas")
public class PreguntaEntity {

    // nota : Atributos de la tabla

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pregunta;
    private String tipoPregunta;
    private int tiempo;
    private int valor;

    // nota : relación con la tabla evaluaciones
    @JsonBackReference("evaluacion-preguntas")
    @ManyToOne
    @JoinColumn(name = "idEvaluacion")
    private EvaluacionesEntity evaluacion;

    // nota : relación con la tabla opciones_respuestas
    @JsonManagedReference("pregunta-opciones")
    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpcionRespuestaEntity> opcionRespuestas;

    // nota : relación con la tabla  RespuestaEstudianteEntity
    @OneToMany(mappedBy = "pregunta")
    private List<RespuestaEstudianteEntity> respuestaEstudiantes;

    public PreguntaEntity(Long id) {
        this.id = id;
    }
}
