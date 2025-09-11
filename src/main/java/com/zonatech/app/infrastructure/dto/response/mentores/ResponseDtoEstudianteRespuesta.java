package com.zonatech.app.infrastructure.dto.response.mentores;

import com.zonatech.app.domain.models.EvaluacionRef;
import com.zonatech.app.domain.models.RespuestaEstudiante;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDtoEstudianteRespuesta {
    private Long id;
    private EvaluacionRef estudiante;
    private List<RespuestaEstudiante> respuestaEstudiantes;
    private String feedback;
}
