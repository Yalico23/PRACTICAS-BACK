package com.zonatech.app.application.services;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.models.ResponseEntrevistaEstudianteIA;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.EvaluarEntrevistaEstudianteUseCase;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.GenerarResumenIAUseCase;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.ListarEntrevistaEstudianteUseCase;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.ResponderEntrevistaUseCase;
import com.zonatech.app.infrastructure.dto.request.entrevistaestudiante.EntrevistaEstudiantesEntityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class EntrevistaEstudianteServices implements
        ResponderEntrevistaUseCase,
        ListarEntrevistaEstudianteUseCase,
        GenerarResumenIAUseCase,
        EvaluarEntrevistaEstudianteUseCase {

    private final ResponderEntrevistaUseCase responderEntrevistaUseCase;
    private final ListarEntrevistaEstudianteUseCase listarEntrevistaEstudianteUseCase;
    private final GenerarResumenIAUseCase generarResumenIAUseCase;
    private final EvaluarEntrevistaEstudianteUseCase evaluarEntrevistaEstudianteUseCase;

    public EntrevistaEstudianteServices(ResponderEntrevistaUseCase responderEntrevistaUseCase, ListarEntrevistaEstudianteUseCase listarEntrevistaEstudianteUseCase, GenerarResumenIAUseCase generarResumenIAUseCase, EvaluarEntrevistaEstudianteUseCase evaluarEntrevistaEstudianteUseCase) {
        this.responderEntrevistaUseCase = responderEntrevistaUseCase;
        this.listarEntrevistaEstudianteUseCase = listarEntrevistaEstudianteUseCase;
        this.generarResumenIAUseCase = generarResumenIAUseCase;
        this.evaluarEntrevistaEstudianteUseCase = evaluarEntrevistaEstudianteUseCase;
    }

    @Transactional
    @Override
    public void responderEntrevista(Long idEntrevista, Long idUsuario, MultipartFile video) {
        responderEntrevistaUseCase.responderEntrevista(idEntrevista, idUsuario, video);
    }

    @Transactional(readOnly = true)
    @Override
    public EntrevistaEstudiante findById(Long id) {
        return listarEntrevistaEstudianteUseCase.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ResponseDtoEntrevistaPendientes> listEntrevistaEstudianteByIdEvaluacion(Long idEntrevista, String filter, Pageable pageable) {
        return listarEntrevistaEstudianteUseCase.listEntrevistaEstudianteByIdEvaluacion(idEntrevista, filter, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public EntrevistaEstudiante findByIdEntrevistaAndIdEstudiante(Long idEntrevista, Long idEstudiante) {
        return listarEntrevistaEstudianteUseCase.findByIdEntrevistaAndIdEstudiante(idEntrevista, idEstudiante);
    }


    @Override
    public ResponseEntrevistaEstudianteIA generarResumenIA(String resumen) {
        return generarResumenIAUseCase.generarResumenIA(resumen);
    }

    @Transactional
    @Override
    public void evaluarEntrevistaEstudiante(Long idEntrevista, int valoracion, String feedback) {
        evaluarEntrevistaEstudianteUseCase.evaluarEntrevistaEstudiante(idEntrevista, valoracion, feedback);
    }

    @Transactional
    @Override
    public EntrevistaEstudiante update(EntrevistaEstudiantesEntityDto entrevistaEstudiante) {
        return evaluarEntrevistaEstudianteUseCase.update(entrevistaEstudiante);
    }
}
