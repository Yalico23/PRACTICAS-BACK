package com.zonatech.app.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "opcionesRespuestas")
public class OpcionRespuestaEntity {

    // nota : Atributos de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String opcionRespuesta;
    private boolean correcta;

    // nota : relación con la tabla preguntas
    @JsonBackReference("pregunta-opciones")
    @ManyToOne
    @JoinColumn(name = "idPregunta")
    private PreguntaEntity pregunta;


    public OpcionRespuestaEntity(Long id) {
        this.id = id;
    }
}
