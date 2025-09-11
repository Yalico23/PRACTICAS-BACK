package com.zonatech.app.application.usecases.evaluacionEstudiante;

import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.ResponserEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class ResponserEvaluacionUseCaseImpl implements ResponserEvaluacionUseCase {

    private final EvaluacionEstudianteRespositoryPort evaluacionEstudianteRespositoryPort;

    @Override
    public EvaluacionEstudiante responderEvaluacion(EvaluacionEstudiante evaluacionEstudiante) {

        Long idEstudiante = evaluacionEstudiante.getEstudiante().getId();
        Long idEvaluacion = evaluacionEstudiante.getEvaluacion().getId();

        if (evaluacionEstudianteRespositoryPort.existsByEvaluacionIdAndEstudianteId(idEvaluacion, idEstudiante)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante ya ha respondido la evaluación");
        }

        evaluacionEstudiante.setCompletado(true);
        evaluacionEstudiante.setFechaInicio(LocalDate.now());

        log.info(evaluacionEstudiante.toString());
        return evaluacionEstudianteRespositoryPort.save(evaluacionEstudiante);
    }

    @Override
    public void EvaluacionRespondida(Long idEvaluacion, Long idEstudiante) {
        if (evaluacionEstudianteRespositoryPort.existsByEvaluacionIdAndEstudianteId(idEvaluacion, idEstudiante)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante ya ha respondido la evaluación");
        }
    }


}
