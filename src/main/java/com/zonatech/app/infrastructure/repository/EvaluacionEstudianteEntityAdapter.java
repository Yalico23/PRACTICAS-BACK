package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.*;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import com.zonatech.app.infrastructure.entities.EvaluacionEstudianteEntity;
import com.zonatech.app.infrastructure.mappers.EvaluacionEstudianteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class EvaluacionEstudianteEntityAdapter implements EvaluacionEstudianteRespositoryPort {

    private final EvaluacionEstudianteEntityRepository entityRepository;
    private final EvaluacionEstudianteMapper evaluacionEstudianteMapper;

    @Override
    public EvaluacionEstudiante save(EvaluacionEstudiante evaluacionEstudiante) {
        EvaluacionEstudianteEntity entity = evaluacionEstudianteMapper.toEntity(evaluacionEstudiante);
        return evaluacionEstudianteMapper.toModel(entityRepository.save(entity));

    }

    @Override
    public boolean existsByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId) {
        return entityRepository.existsByEvaluacionIdAndEstudianteId(evaluacionId, estudianteId);
    }

    @Override
    public boolean existsByEvaluacionId(Long evaluacionId) {
        return entityRepository.existsByEvaluacionId(evaluacionId);
    }

    @Override
    public List<Object[]> listEvaluacionesPendientesByEvaluacionId(Long evaluacionId) {
        return entityRepository.findEvaluacionesByIdEvaluacion(evaluacionId);
    }

    @Override
    public Optional<EvaluacionEstudiante> findById(Long id) {

        Optional<EvaluacionEstudianteEntity> entityOptional = entityRepository.findById(id);

        return entityOptional.map(evaluacionEstudianteMapper::toModel);
    }

    @Override
    public EvaluacionEstudiante update(EvaluacionEstudiante evaluacionEstudiante) {
        return null;
    }

    @Override
    public EvaluacionEstudiante findByEvaluacionIdAndEstudianteId(Long evaluacionId, Long estudianteId) {
        return evaluacionEstudianteMapper
                .toModel(entityRepository.findByEvaluacionIdAndEstudianteId(evaluacionId, estudianteId));
    }

    @Override
    public PromedioGeneralDtoEstudiante getPromedioGeneralByIdEstudiante(Long idEstudiante) {
        return entityRepository.gePromedioGeneralDtoEstudiante(idEstudiante);
    }

    @Override
    public List<ComparacionPromedioGeneralEvalu> getComparacionPromedioGeneralEvalu(Long idEvaluacion) {
        return entityRepository.getComparacionPromedioGeneralEvalu(idEvaluacion);
    }

    @Override
    public List<ProgresoMensualEstudiante> getProgresoMensualEstudiante(Long idEstudiante) {
        return entityRepository.getProgresoMensualEstudiantes(idEstudiante);
    }

    @Override
    public List<ResumenEvalucionMentor> getResumenEvaluacionMentor(Long idMentor) {
        return entityRepository.getResumenEvalucionMentors(idMentor);
    }

    @Override
    public List<MejorPeorDesempeno> getMejorPeorDesempenos(Long idMentor) {
        return entityRepository.getMejorPeorDesempenos(idMentor);
    }
}
