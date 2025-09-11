package com.zonatech.app.application.services;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.domain.ports.input.evaluaciones.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluacionServices implements
        CrearEvaluacionUseCase,
        ListarEvaluacionesUseCase,
        CambiarEstadoEvaluacionUseCase,
        EliminarEvaluacionUseCase,
        EditarEvaluacionUseCase {

    private final CrearEvaluacionUseCase crearEvaluacionUseCase;
    private final ListarEvaluacionesUseCase listarEvaluacionesUseCase;
    private final CambiarEstadoEvaluacionUseCase cambiarEstadoEvaluacionUseCase;
    private final EliminarEvaluacionUseCase eliminarEvaluacionUseCase;
    private final EditarEvaluacionUseCase editarEvaluacionUseCase;


    public EvaluacionServices(CrearEvaluacionUseCase crearEvaluacionUseCase, ListarEvaluacionesUseCase listarEvaluacionesUseCase, CambiarEstadoEvaluacionUseCase cambiarEstadoEvaluacionUseCase, EliminarEvaluacionUseCase eliminarEvaluacionUseCase, EditarEvaluacionUseCase editarEvaluacionUseCase) {
        this.crearEvaluacionUseCase = crearEvaluacionUseCase;
        this.listarEvaluacionesUseCase = listarEvaluacionesUseCase;
        this.cambiarEstadoEvaluacionUseCase = cambiarEstadoEvaluacionUseCase;
        this.eliminarEvaluacionUseCase = eliminarEvaluacionUseCase;
        this.editarEvaluacionUseCase = editarEvaluacionUseCase;
    }

    @Transactional
    @Override
    public Evaluacion crearEvaluacion(Evaluacion evaluacion, Long mentorId) {
        return crearEvaluacionUseCase.crearEvaluacion(evaluacion, mentorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluacion> findAllEvaluaciones() {
        return listarEvaluacionesUseCase.findAllEvaluaciones();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Evaluacion> findEvaluacionesByMentorId(Long idMentor) {
        return listarEvaluacionesUseCase.findEvaluacionesByMentorId(idMentor);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Evaluacion> findEvaluacionesByMentorIdPageable(String email, String filtro, Pageable pageable) {
        return listarEvaluacionesUseCase.findEvaluacionesByMentorIdPageable(email, filtro, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EvaluacionResumen> listarEvaluacionesToEstudiantes(String emailEstudiante, String filtro, Pageable pageable) {
        return listarEvaluacionesUseCase.listarEvaluacionesToEstudiantes(emailEstudiante, filtro, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Evaluacion findEvaluacionById(Long idEvaluacion) {
        return listarEvaluacionesUseCase.findEvaluacionById(idEvaluacion);
    }

    @Transactional
    @Override
    public Evaluacion cambiarEstadoEvaluacion(Long idEvaluacion, boolean estadoPrevio) {
        return cambiarEstadoEvaluacionUseCase.cambiarEstadoEvaluacion(idEvaluacion, estadoPrevio);
    }

    @Transactional
    @Override
    public void eliminarEvaluacion(Long idEvaluacion) {
        eliminarEvaluacionUseCase.eliminarEvaluacion(idEvaluacion);
    }

    @Transactional
    @Override
    public Evaluacion update(Evaluacion evaluacion, Long idMentor) {
        return editarEvaluacionUseCase.update(evaluacion, idMentor);
    }
}
