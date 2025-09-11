package com.zonatech.app.infrastructure.dto.response.mentores;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDtoEvaluacionesPendientesById {
    private Long id;
    private String titulo;
    private String tags;
    private LocalDate fechaCreacion;
}
