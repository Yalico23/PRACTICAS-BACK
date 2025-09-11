package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.application.services.EntrevistaEstudianteServices;
import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.models.ResponseEntrevistaEstudianteIA;
import com.zonatech.app.infrastructure.dto.response.mentores.ResponseEntrevistaEstudiante;
import com.zonatech.app.infrastructure.mappers.EntrevistaEstudianteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/entrevistaEstudiante")
@RequiredArgsConstructor
public class EntrevistaEstudianteController {

    private final EntrevistaEstudianteServices entrevistaEstudianteServices;
    private final EntrevistaEstudianteMapper mapper;

    @PreAuthorize("hasRole('ESTUDIANTE')")
    @PostMapping("/guardarGrabacion")
    public ResponseEntity<Void> crearEntrevista(
            @RequestParam("video") MultipartFile videoFile,
            @RequestParam("entrevistaId") String entrevistaId,
            @RequestParam("usuarioId") String usuarioId) {

        entrevistaEstudianteServices.responderEntrevista(
                Long.parseLong(entrevistaId),
                Long.parseLong(usuarioId),
                videoFile
        );
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/buscarEntrevistaEstudiante")
    public ResponseEntity<ResponseEntrevistaEstudiante> getById(
            @RequestParam("idEntrevistaEstudiante") Long idEntrevistaEstudiante
    ) {
        return ResponseEntity.ok(mapper.toResponse(entrevistaEstudianteServices.findById(idEntrevistaEstudiante)));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/entrevistasPendientes")
    public ResponseEntity<Page<ResponseDtoEntrevistaPendientes>> entrevistasPrendientes
            (@RequestParam("idEntrevista") Long idEntrevista,
             @RequestParam(name = "filter", required = false) String filter,
             @RequestParam(name = "page", defaultValue = "0") Integer page,
             @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(entrevistaEstudianteServices.listEntrevistaEstudianteByIdEvaluacion(idEntrevista, filter, pageable));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/generarResumenEntrevistaIA")
    public ResponseEntity<ResponseEntrevistaEstudianteIA> generarResumen(
            @RequestParam("texto") String texto
    ) {
        return ResponseEntity.ok(entrevistaEstudianteServices.generarResumenIA(texto));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/evaluarEntrevista")
    public ResponseEntity<Void> evaluarEntrevista
            (@RequestParam("idEntrevistaEstudiante") Long idEntrevistaEstudiante,
             @RequestParam("feedback") String feedback,
             @RequestParam("valoracion") int valoracion) {
        entrevistaEstudianteServices.evaluarEntrevistaEstudiante(idEntrevistaEstudiante,valoracion,feedback);
        return ResponseEntity.ok().build();
    }
}
