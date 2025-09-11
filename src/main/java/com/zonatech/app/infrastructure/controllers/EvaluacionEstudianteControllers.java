package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.application.services.EvaluacionEstudianteServices;
import com.zonatech.app.domain.models.AnalisisRespuestasTextoRequest;
import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.DtoRequestEvaluarEvaluacion;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.RequestDtoCrearEvaluacionEstudiante;
import com.zonatech.app.infrastructure.dto.response.IA.AnalisisRespuestasTextoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseDtoEstudianteRespuesta;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseDtoEvaluacionPendientes;
import com.zonatech.app.infrastructure.mappers.EvaluacionEstudianteMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluacionEstudiante")
@RequiredArgsConstructor
@Slf4j
public class EvaluacionEstudianteControllers {

    private final static Logger logger = LoggerFactory.getLogger(EvaluacionEstudianteControllers.class);
    private final EvaluacionEstudianteServices evaluacionEstudianteServices;
    private final EvaluacionEstudianteMapper estudianteMapper;

    @PreAuthorize("hasRole('ESTUDIANTE')")
    @PostMapping("/mandarEvaluacion")
    public ResponseEntity<EvaluacionEstudiante> mandarEvaluacion
            (@Valid @RequestBody RequestDtoCrearEvaluacionEstudiante evaluacionEstudiante) {

        EvaluacionEstudiante evaluacion = estudianteMapper.toModel(evaluacionEstudiante);
        EvaluacionEstudiante response = evaluacionEstudianteServices.responderEvaluacion(evaluacion);

        return ResponseEntity
                .ok()
                .body(response);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/calificarEvaluacion")
    public ResponseEntity<EvaluacionEstudiante> calificarEvaluacion
            (@RequestBody DtoRequestEvaluarEvaluacion dtoRequest){
        EvaluacionEstudiante calificaredEvaluacion = evaluacionEstudianteServices.calificarEvaluacion(dtoRequest);
        return ResponseEntity.ok(calificaredEvaluacion);
    }


    @PreAuthorize("hasRole('ESTUDIANTE')")
    @PostMapping("/validarEvaluacionDoble/{idEvaluacion}/{idEstudiante}")
    public ResponseEntity<Boolean> validarEvaluacionDoble(
            @PathVariable("idEvaluacion") Long idEvaluacion,
            @PathVariable("idEstudiante") Long idEstudiante) {
        evaluacionEstudianteServices.EvaluacionRespondida(idEvaluacion, idEstudiante);
        return ResponseEntity.ok(true);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/evaluacionesPendientes/{idEvalucion}")
    public ResponseEntity<List<ResponseDtoEvaluacionPendientes>> evaluacionesPendientes
            (@PathVariable("idEvalucion") Long idEvalucion) {

        List<Object[]> evaluacionesData = evaluacionEstudianteServices
                .listEvaluacionesEstudianteByIdEvaluacion(idEvalucion);

        List<ResponseDtoEvaluacionPendientes> response = evaluacionesData.stream()
                .map(obj -> new ResponseDtoEvaluacionPendientes(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        obj[2] == null ? null : ((Number) obj[2]).longValue()
        )).toList();

        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasAnyRole('MENTOR', 'ESTUDIANTE')")
    @GetMapping("/evaluacionesEstudiante/{idEvaluacionEstudiante}")
    public ResponseEntity<ResponseDtoEstudianteRespuesta> evaluacionesEstudiante
            (@PathVariable("idEvaluacionEstudiante") Long idEvaluacionEstudiante) {

        EvaluacionEstudiante evaluacion = evaluacionEstudianteServices
                .findById(idEvaluacionEstudiante);

        return ResponseEntity.ok(estudianteMapper.toResponseDto(evaluacion));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/analizar-respuestas-texto")
    public ResponseEntity<AnalisisRespuestasTextoResponse> analizarRespuestasTexto(
            @Valid @RequestBody AnalisisRespuestasTextoRequest request) {

        try {
            if (request.getRespuestasTexto() == null || request.getRespuestasTexto().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(AnalisisRespuestasTextoResponse.builder()
                                .success(false)
                                .message("No hay respuestas de texto para analizar")
                                .build());
            }

            AnalisisRespuestasTextoResponse response = evaluacionEstudianteServices.analizarRespuestasTexto(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error al analizar respuestas con IA: ", e);
            return ResponseEntity.internalServerError()
                    .body(AnalisisRespuestasTextoResponse.builder()
                            .success(false)
                            .message("Error interno del servidor: " + e.getMessage())
                            .build());
        }
    }

}
