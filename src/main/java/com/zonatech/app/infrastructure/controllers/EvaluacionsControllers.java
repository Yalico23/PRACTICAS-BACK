package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.application.services.EvaluacionServices;
import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.infrastructure.dto.request.evaluacion.CrearEvaluacionRequestDTO;
import com.zonatech.app.infrastructure.dto.request.evaluacion.EditarEvaluacionRequestDTO;
import com.zonatech.app.infrastructure.dto.response.estudiante.EvaluacionesDtoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.CreateEvaluacionDtoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.ListEvaluacionesByMentoresDtoResponse;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseDtoEvaluacionesPendientesById;
import com.zonatech.app.infrastructure.mappers.EvaluacionMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/evaluaciones")
@RequiredArgsConstructor
public class EvaluacionsControllers {

    private final EvaluacionMapper evaluacionMapper;
    private final EvaluacionServices evaluacionServices;

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/crear")
    public ResponseEntity<CreateEvaluacionDtoResponse> crearEvaluacion
            (@Valid @RequestBody CrearEvaluacionRequestDTO requestDTO) {

        Evaluacion evaluacion = evaluacionMapper.toModelEvaluacion(requestDTO);
        CreateEvaluacionDtoResponse dtoResponse =
                evaluacionMapper.toEvaluacionCreadaDtoResponse(evaluacionServices.crearEvaluacion(evaluacion, requestDTO.mentorId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/actualizar")
    public ResponseEntity<CreateEvaluacionDtoResponse> actualizarEvaluacion
            (@Valid @RequestBody EditarEvaluacionRequestDTO requestDTO) {

        Evaluacion evaluacion = evaluacionMapper.toModelEvaluacion(requestDTO);
        CreateEvaluacionDtoResponse dtoResponse =
                evaluacionMapper.toEvaluacionCreadaDtoResponse(evaluacionServices.update(evaluacion, requestDTO.getMentorId()));

        return ResponseEntity.ok(dtoResponse);
    }

    // err : Eliminar este método, ya que no es necesario
    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/listar")
    public ResponseEntity<List<ListEvaluacionesByMentoresDtoResponse>> crearEvaluacion() {
        return ResponseEntity.ok(evaluacionMapper
                .toListEvaluacion(evaluacionServices.findAllEvaluaciones()));
    }

    // nota : Modificar a Pageable
    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/listarById")
    public ResponseEntity<List<ListEvaluacionesByMentoresDtoResponse>> listByIdEvaluaciones
            (@RequestParam("idMentor") Long idMentor) {

        List<Evaluacion> evaluacionList = evaluacionServices.findEvaluacionesByMentorId(idMentor);
        return ResponseEntity.ok(evaluacionMapper.toListEvaluacion(evaluacionList));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/listPageableById")
    public ResponseEntity<Page<Evaluacion>> listPageableByIdEvaluaciones
            (@RequestParam(name = "emailMentor") String emailMentor,
             @RequestParam(name = "page", defaultValue = "0") Integer page,
             @RequestParam(name = "size", defaultValue = "5") Integer size,
             @RequestParam(name = "filter", required = false) String filter) {

        Pageable pageable = PageRequest.of(page,size, Sort.by("id").descending());
        Page<Evaluacion> resultPage = evaluacionServices
                .findEvaluacionesByMentorIdPageable(emailMentor, filter,pageable);

        return ResponseEntity.ok(resultPage);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/listPendingEvaluaciones")
    public ResponseEntity<List<ResponseDtoEvaluacionesPendientesById>> listPendingEvaluacionesById
            (@RequestParam("idMentor") Long idMentor) {

        if (idMentor == null) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ResponseDtoEvaluacionesPendientesById> response = evaluacionMapper
                .toPendientesById(evaluacionServices.findEvaluacionesByMentorId(idMentor));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/cambiarEstado")
    public ResponseEntity<Map<String, Object>> cambiarEstadoEvaluacion
            (@RequestParam("idEvaluacion") Long idEvaluacion,
             @RequestParam("estadoPrevio") boolean estadoPrevio) {

        evaluacionServices.cambiarEstadoEvaluacion(idEvaluacion, estadoPrevio);
        Map<String, Object> response = new HashMap<>();

        String mensaje = estadoPrevio
                ? "La evaluación está deshabilitada"
                : "La evaluación está habilitada";


        response.put("message", mensaje);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/listarEvaluacionesEstudiante")
    public ResponseEntity<Page<EvaluacionResumen>> listarEvaluacionesEstudiante
            (@RequestParam("emailEstudiante") String emailEstudiante,
             @RequestParam(name = "page", defaultValue = "0") Integer page,
             @RequestParam(name = "size", defaultValue = "5") Integer size,
             @RequestParam(name = "filter", required = false) String filter) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<EvaluacionResumen> evaluacionesPage = evaluacionServices
                .listarEvaluacionesToEstudiantes(emailEstudiante, filter, pageable);

        return ResponseEntity.ok(evaluacionesPage);
    }

    @PreAuthorize("hasAnyRole('MENTOR', 'ESTUDIANTE')")
    @GetMapping("/listarEvaluacionById")
    public ResponseEntity<Evaluacion> listarEvaluacionById
            (@RequestParam("idEvaluacion") Long idEvaluacion) {
        Evaluacion evaluacion = evaluacionServices.findEvaluacionById(idEvaluacion);
        return ResponseEntity.ok(evaluacion);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @DeleteMapping("/eliminarEvaluacion")
    public ResponseEntity<Map<String, Object>> eliminarEvaluacion
            (@RequestParam("idEvaluacion") Long idEvaluacion) {

        evaluacionServices.eliminarEvaluacion(idEvaluacion);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "La evaluación ha sido eliminada correctamente");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
