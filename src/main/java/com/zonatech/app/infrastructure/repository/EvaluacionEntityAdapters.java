package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.Evaluacion;
import com.zonatech.app.domain.models.EvaluacionResumen;
import com.zonatech.app.domain.ports.output.EvaluacionesRepositoryPort;
import com.zonatech.app.infrastructure.entities.EvaluacionesEntity;
import com.zonatech.app.infrastructure.mappers.EvaluacionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EvaluacionEntityAdapters implements EvaluacionesRepositoryPort {

    private final EvaluacionesEntityRepository entityRepository;
    private final EvaluacionMapper evaluacionMapper;

    @Override
    public Evaluacion save(Evaluacion evaluacion) {
        EvaluacionesEntity evaluacionesEntity = evaluacionMapper.toEvaluacionesEntity(evaluacion);
        return evaluacionMapper.toModelEvaluacion(entityRepository.save(evaluacionesEntity));
    }

    @Override
    public List<Evaluacion> findAllEvaluaciones() {
        return evaluacionMapper.toEvaluacionList(entityRepository.findAll());
    }

    @Override
    public List<Evaluacion> findAllEvaluacionesById(Long id) {
        List<EvaluacionesEntity> evaluacionesEntities = entityRepository.findByMentorId(id);
        return evaluacionMapper.toEvaluacionList(evaluacionesEntities);
    }

    @Override
    public Optional<Evaluacion> findById(Long id) {
        return evaluacionMapper.toOptionalEvaluacion(entityRepository.findById(id));
    }

    @Override
    public Page<EvaluacionResumen> findEvaluacionesResumenRaw(String emailEstudiante, String filter, Pageable pageable) {
        return entityRepository.findEvaluacionesResumen(emailEstudiante, filter, pageable);
    }

    @Override
    public void deleteById(Long id) {
        entityRepository.deleteById(id);
    }

    @Override
    public Page<Evaluacion> findByMentorIdWithFilter(String email, String filter, Pageable pageable) {
        return entityRepository.findByMentorIdWithFilter(
                        email, filter, pageable)
                .map(evaluacionMapper::toModelEvaluacion);
    }
}
