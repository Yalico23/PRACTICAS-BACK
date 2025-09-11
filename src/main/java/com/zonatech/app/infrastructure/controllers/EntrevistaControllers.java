package com.zonatech.app.infrastructure.controllers;

import com.zonatech.app.application.services.EntrevistaServices;
import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import com.zonatech.app.infrastructure.dto.request.entrevista.DTORequestEntrevistaUpdate;
import com.zonatech.app.infrastructure.mappers.EntrevistaMapper;
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

@RestController
@RequestMapping("/api/entrevistas")
@RequiredArgsConstructor
public class EntrevistaControllers {

    private final EntrevistaServices entrevistaServices;
    private final EntrevistaMapper entrevistaMapper;

    @PreAuthorize("hasRole('MENTOR')")
    @PostMapping("/crear")
    public ResponseEntity<Entrevista> crearEntrevista(@Valid @RequestBody Entrevista entrevista) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entrevistaServices.crearEntrevista(entrevista));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/modificar")
    public ResponseEntity<Entrevista> modificarEntrevista(@Valid @RequestBody DTORequestEntrevistaUpdate entrevista) {
        Entrevista updatedEntrevista = entrevistaMapper.toModel(entrevista);
        return ResponseEntity.ok(entrevistaServices.updateEntrevista(updatedEntrevista));
    }

    @PreAuthorize("hasRole('MENTOR')")
    @DeleteMapping("/eliminar/{idEntrevista}")
    public ResponseEntity<Void> eliminarEntrevista(@PathVariable("idEntrevista") Long idEntrevista) {
        entrevistaServices.elimnarEntrevista(idEntrevista);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasRole('MENTOR')")
    @GetMapping("/listPageableById")
    public ResponseEntity<Page<Entrevista>> listPageableById(
            @RequestParam("emailMentor") String emailMentor,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "filter", required = false) String filter) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Entrevista> resultPage = entrevistaServices.findEntrevistasByMentorIdPageable(emailMentor, filter, pageable);

        return ResponseEntity.ok(resultPage);
    }

    @PreAuthorize("hasAnyRole('MENTOR','ESTUDIANTE')")
    @GetMapping("/listEntrevistasById")
    public ResponseEntity<Entrevista> findEntrevistaById(@RequestParam("idEntrevista") Long idEntrevista) {
        Entrevista entrevista = entrevistaServices.findEntrevistaById(idEntrevista);
        return ResponseEntity.ok(entrevista);
    }

    @PreAuthorize("hasRole('MENTOR')")
    @PutMapping("/cambiarEstadoEntrevista")
    public ResponseEntity<Entrevista> cambairEstadoEntrevista
            (@RequestParam("idEntrevista") Long idEntrevista, @RequestParam("estadoPrev") Boolean estadoPrev) {
        return ResponseEntity.ok(entrevistaServices.cambiarEstadoEntrevista(idEntrevista, estadoPrev));
    }

    @PreAuthorize("hasRole('ESTUDIANTE')")
    @GetMapping("/listarEntrevistaEstudiante")
    public ResponseEntity<Page<EntrevistaResumen>> listarEntrevistaResumen
            (@RequestParam("emailEstudiante") String emailEstudiante,
             @RequestParam(name = "page", defaultValue = "0") Integer page,
             @RequestParam(name = "size", defaultValue = "5") Integer size,
             @RequestParam(name = "filter", required = false) String filter) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<EntrevistaResumen> resumenPage = entrevistaServices
                .listarEntrevistasEstudiante(emailEstudiante, filter, pageable);

        return ResponseEntity.ok(resumenPage);
    }

}
