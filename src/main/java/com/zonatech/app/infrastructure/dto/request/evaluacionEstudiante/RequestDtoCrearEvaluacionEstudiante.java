package com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante;

import com.zonatech.app.domain.models.EvaluacionRef;
import com.zonatech.app.domain.models.RespuestaEstudiante;
import com.zonatech.app.domain.models.UsuarioRef;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDtoCrearEvaluacionEstudiante {
    private UsuarioRef estudiante;
    private EvaluacionRef evaluacion;
    private List<RespuestaEstudiante> respuestaEstudiantes;
}
