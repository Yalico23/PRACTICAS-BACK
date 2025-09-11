package com.zonatech.app.infrastructure.dto.response.mentores;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDtoEvaluacionPendientes {
    private Long idEvaluacionEstudiante;
    private String nombreEstudiante;
    private Long notaFinal;
}
