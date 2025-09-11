package com.zonatech.app.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "preguntaEntrevistas")
public class PreguntaEntrevistaEntity {

    // nota : atributos de la tabla

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pregunta;
    private int tiempo;
    private int valor;

    // nota : relación con la tabla entrevistas (entrevista)
    @ManyToOne
    @JoinColumn(name = "idEntrevista")
    private EntrevistaEntity entrevista;
}
