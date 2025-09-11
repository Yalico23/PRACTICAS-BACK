package com.zonatech.app.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    // nota : atributos en la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaCreacion;
    private boolean habilitado;
    private String password;
    private String email;
    private String token;

    // nota : relación con la tabla roles (mentor , estudiante)
    @JsonIgnoreProperties({"users", "handler", "hibernateLazyInitializer"})
    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role_id"})}
    )
    private List<RolesEntity> roles;

    // nota : relación con la tabla entrevistas (mentor)
    @OneToMany(mappedBy = "mentor")
    private List<EntrevistaEntity> entrevistas;

    // nota : relación con la tabla entrevistaEstudiantes (estudiante)
    @OneToMany(mappedBy = "estudiante")
    private List<EntrevistaEstudiantesEntity> entrevistaEstudiantes;

    // nota : relación con la tabla evaluaciones (mentor)
    @JsonManagedReference("usuario-evaluaciones")
    @OneToMany(mappedBy = "mentor")
    private List<EvaluacionesEntity> evaluaciones;

    // nota : relación con la tabla evaluaiconEstudiante (estudiante)
    @OneToMany(mappedBy = "estudiante")
    private List<EvaluacionEstudianteEntity> evaluacionEstudiantes;

    // --------------------------------------------------------------------------------

    // nota : datos no persistidos (validar si es admin)
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean mentor;
}
