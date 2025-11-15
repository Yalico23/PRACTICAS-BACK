    package com.zonatech.app.infrastructure.entities;
    
    import jakarta.persistence.*;
    import lombok.*;
    
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    @Table(name = "respuestaEstudiante")
    public class RespuestaEstudianteEntity {
    
        // nota : atributos de la tabla
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private boolean esCorrecta; // si es correcta o no
        private int nota;

        @Column(name = "respuesta", columnDefinition = "TEXT")
        private String respuesta;
    
        // nota : relación con la tabla preguntas
        @ManyToOne
        @JoinColumn(name = "idPregunta")
        private PreguntaEntity pregunta;

        // nota : relación con evaluacionEstudiante
        @ManyToOne
        @JoinColumn(name = "idEvaluacionEstudiante")
        private EvaluacionEstudianteEntity evaluacionEstudiante;
    
        // nota : relación con la tabla opciones
        @ManyToOne
        @JoinColumn(name = "idOpcion")
        private OpcionRespuestaEntity opcionRespuesta;
    }
