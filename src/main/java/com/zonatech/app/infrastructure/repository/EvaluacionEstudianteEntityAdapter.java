package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.EvaluacionEstudiante;
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
}
