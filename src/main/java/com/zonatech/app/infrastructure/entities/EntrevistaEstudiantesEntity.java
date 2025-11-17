package com.zonatech.app.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entrevistaEstudiantes")
@Entity
public class EntrevistaEstudiantesEntity {

    // nota : atributos de la tabla entrevistaEstudiantes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "TEXT")
    private String feedBack;
    private boolean completado;
    private int valoracion;
    private String URLVideo;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String videoResumen;
    private LocalDate fechaEntrevista;

    // nota : relación con la tabla entrevistas
    @ManyToOne
    @JoinColumn(name = "idEntrevista")
    private EntrevistaEntity entrevista;

    // nota : relación con la tabla usuarios (estudiante)
    @ManyToOne
    @JoinColumn(name = "idEstudiante")
    private UsuarioEntity estudiante;
}
