package com.zonatech.app.application.services;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import com.zonatech.app.domain.ports.input.entrevista.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntrevistaServices implements
        CrearEntrevistaUseCase,
        ModificarEntrevistaUseCase,
        EliminarEntrevistaUseCase,
        ListarEntrevistasUseCase,
        CambiarEstadoEntrevistaUseCase {

    private final CrearEntrevistaUseCase crearEntrevistaUseCase;
    private final ModificarEntrevistaUseCase modificarentrevista;
    private final EliminarEntrevistaUseCase eliminarEntrevistaUseCase;
    private final ListarEntrevistasUseCase listarEntrevistasUseCase;
    private final CambiarEstadoEntrevistaUseCase cambiarEstadoEntrevistaUseCase;

    public EntrevistaServices(CrearEntrevistaUseCase crearEntrevistaUseCase, ModificarEntrevistaUseCase modificarentrevista, EliminarEntrevistaUseCase eliminarEntrevistaUseCase, ListarEntrevistasUseCase listarEntrevistasUseCase, CambiarEstadoEntrevistaUseCase cambiarEstadoEntrevistaUseCase) {
        this.crearEntrevistaUseCase = crearEntrevistaUseCase;
        this.modificarentrevista = modificarentrevista;
        this.eliminarEntrevistaUseCase = eliminarEntrevistaUseCase;
        this.listarEntrevistasUseCase = listarEntrevistasUseCase;
        this.cambiarEstadoEntrevistaUseCase = cambiarEstadoEntrevistaUseCase;
    }

    @Transactional
    @Override
    public Entrevista crearEntrevista(Entrevista entrevista) {
        return crearEntrevistaUseCase.crearEntrevista(entrevista);
    }

    @Transactional
    @Override
    public Entrevista updateEntrevista(Entrevista entrevista) {
        return modificarentrevista.updateEntrevista(entrevista);
    }

    @Transactional
    @Override
    public void elimnarEntrevista(Long idEntrevista) {
        eliminarEntrevistaUseCase.elimnarEntrevista(idEntrevista);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Entrevista> findEntrevistasByMentorIdPageable(String email, String filtro, Pageable pageable) {
        return listarEntrevistasUseCase.findEntrevistasByMentorIdPageable(email, filtro, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Entrevista findEntrevistaById(Long id) {
        return listarEntrevistasUseCase.findEntrevistaById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EntrevistaResumen> listarEntrevistasEstudiante(String email, String filtro, Pageable pageable) {
        return listarEntrevistasUseCase.listarEntrevistasEstudiante(email, filtro, pageable);
    }

    @Override
    public List<Entrevista> listByMentorEmail(String email) {
        return listarEntrevistasUseCase.listByMentorEmail(email);
    }

    @Transactional
    @Override
    public Entrevista cambiarEstadoEntrevista(Long idEntrevista, boolean estadoPrevio) {
        return cambiarEstadoEntrevistaUseCase.cambiarEstadoEntrevista(idEntrevista, estadoPrevio);
    }
}
