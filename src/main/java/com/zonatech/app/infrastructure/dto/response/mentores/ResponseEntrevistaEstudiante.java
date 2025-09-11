package com.zonatech.app.infrastructure.dto.response.mentores;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntrevistaEstudiante {
    private Long id;
    private String URLVideo;
    private String videoResumen;
    private LocalDate fechaEntrevista;
    private String feedBack;
    private int valoracion;
}
