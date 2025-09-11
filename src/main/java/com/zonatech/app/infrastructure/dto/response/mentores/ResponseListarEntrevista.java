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
public class ResponseListarEntrevista {
    private Long id;
    private String titulo;
    private LocalDate fechaCreacion;
}
