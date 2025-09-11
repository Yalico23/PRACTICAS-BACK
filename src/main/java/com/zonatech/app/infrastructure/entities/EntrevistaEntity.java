package com.zonatech.app.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "entrevistas")
public class EntrevistaEntity {

    // nota : atributos en la tabla

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", nullable = false, length = 100) // título de la entrevista
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private boolean activo;

    // nota : relación con la tabla usuarios (mentor)
    @ManyToOne
    @JoinColumn(name = "idMentor")
    private UsuarioEntity mentor;

    // nota : relación con la tabla preguntas (entrevista)
    @OneToMany(mappedBy = "entrevista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PreguntaEntrevistaEntity> preguntas;

    // nota : relación con la tabla entrevistaEstudiantes
    @OneToMany(mappedBy = "entrevista" )
    private List<EntrevistaEstudiantesEntity> entrevistasEstudiantes;

}
