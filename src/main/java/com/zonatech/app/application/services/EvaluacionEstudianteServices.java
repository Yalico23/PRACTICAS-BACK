package com.zonatech.app.application.services;

import com.zonatech.app.domain.models.*;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.ListEvaluacionesEstudianteByIdEvaluacionUseCase;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.ResponserEvaluacionUseCase;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.CalificarEvaluacionUseCase;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.DtoRequestEvaluarEvaluacion;
import com.zonatech.app.infrastructure.dto.response.IA.AnalisisRespuestasTextoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaluacionEstudianteServices implements
        ResponserEvaluacionUseCase,
        ListEvaluacionesEstudianteByIdEvaluacionUseCase,
        CalificarEvaluacionUseCase {

    private final ResponserEvaluacionUseCase responserEvaluacionUseCase;
    private final ListEvaluacionesEstudianteByIdEvaluacionUseCase byIdEvaluacionUseCase;
    private final CalificarEvaluacionUseCase calificarEvaluacionUseCase;

    public EvaluacionEstudianteServices(ResponserEvaluacionUseCase responserEvaluacionUseCase, ListEvaluacionesEstudianteByIdEvaluacionUseCase byIdEvaluacionUseCase, CalificarEvaluacionUseCase calificarEvaluacionUseCase) {
        this.responserEvaluacionUseCase = responserEvaluacionUseCase;
        this.byIdEvaluacionUseCase = byIdEvaluacionUseCase;
        this.calificarEvaluacionUseCase = calificarEvaluacionUseCase;
    }


    @Transactional
    @Override
    public EvaluacionEstudiante responderEvaluacion(EvaluacionEstudiante evaluacionEstudiante) {
        return responserEvaluacionUseCase.responderEvaluacion(evaluacionEstudiante);
    }

    @Override
    public void EvaluacionRespondida(Long idEvaluacion, Long idEstudiante) {
        responserEvaluacionUseCase.EvaluacionRespondida(idEvaluacion, idEstudiante);
    }

    @Override
    public List<Object[]> listEvaluacionesEstudianteByIdEvaluacion(Long idEvaluacion) {
        return byIdEvaluacionUseCase.listEvaluacionesEstudianteByIdEvaluacion(idEvaluacion);
    }

    @Override
    public EvaluacionEstudiante findById(Long id) {
        return byIdEvaluacionUseCase.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public EvaluacionEstudiante findIdEvaluacionEstudiante(Long idEstudiante, Long idEvaluacion) {
        return byIdEvaluacionUseCase.findIdEvaluacionEstudiante(idEstudiante, idEvaluacion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ComparacionMentores> getComparacionMentores(Long idEstudiante) {
        return byIdEvaluacionUseCase.getComparacionMentores(idEstudiante);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ComparacionPromedioGeneralEvalu> getComparacionPromedioGeneralEvalu(Long idEvaluacion) {
        return byIdEvaluacionUseCase.getComparacionPromedioGeneralEvalu(idEvaluacion);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProgresoMensualEstudiante> getProgresoMensualEstudiante(Long idEstudiante) {
        return byIdEvaluacionUseCase.getProgresoMensualEstudiante(idEstudiante);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ResumenEvalucionMentor> getResumenEvaluacionMentor(Long idMentor) {
        return byIdEvaluacionUseCase.getResumenEvaluacionMentor(idMentor);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MejorPeorDesempeno> getMejorPeorDesempenos(Long idMentor) {
        return byIdEvaluacionUseCase.getMejorPeorDesempenos(idMentor);
    }

    @Override
    public AnalisisRespuestasTextoResponse analizarRespuestasTexto(AnalisisRespuestasTextoRequest request) {
        return calificarEvaluacionUseCase.analizarRespuestasTexto(request);
    }

    @Transactional
    @Override
    public EvaluacionEstudiante calificarEvaluacion(DtoRequestEvaluarEvaluacion dtoRequestEvaluarEvaluacion) {
        return calificarEvaluacionUseCase.calificarEvaluacion(dtoRequestEvaluarEvaluacion);
    }
}
