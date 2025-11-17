package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.models.TopEntrevistasEstudiantes;
import com.zonatech.app.domain.ports.output.EntrevistaEstudianteRepositoryPort;
import com.zonatech.app.infrastructure.entities.EntrevistaEstudiantesEntity;
import com.zonatech.app.infrastructure.mappers.EntrevistaEstudianteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EntrevistaEstudianteEntityAdapter implements EntrevistaEstudianteRepositoryPort {

    private final EntrevistaEstudiantesEntityRepository repository;
    private final EntrevistaEstudianteMapper mapper;

    @Override
    public boolean existsByEntrevistaId(Long id) {
        return repository.existsByEntrevistaId(id);
    }

    @Override
    public EntrevistaEstudiante save(EntrevistaEstudiante entrevistaEstudiante) {
        EntrevistaEstudiantesEntity entity = mapper.toEntity(entrevistaEstudiante);
        return mapper.toModel(repository.save(entity));
    }

    @Override
    public Optional<EntrevistaEstudiante> findById(Long id) {
        return mapper.toOptionalModel(repository.findById(id));
    }

    @Override
    public Page<ResponseDtoEntrevistaPendientes> listEntrevistasPendientesByEvaluacionId(Long entrevistaId, String filter, Pageable pageable) {
        return repository.findEntrevistaPendientes(entrevistaId,filter,pageable);
    }

    @Override
    public EntrevistaEstudiante findByEntrevistaIdAndEstudianteId(Long entrevistaId, Long estudianteId) {
        return mapper.toModel(repository.findByEntrevistaIdAndEstudianteId(entrevistaId, estudianteId));
    }

    @Override
    public EntrevistaEstudiante update(EntrevistaEstudiante entrevistaEstudiante) {
        return mapper.toModel(repository.save(mapper.toEntity(entrevistaEstudiante)));
    }

    @Override
    public List<TopEntrevistasEstudiantes> getTopEntrevistasEstudiantes(Long idMentor) {
        return repository.getTopEntrevistasEstudiantes(idMentor);
    }


}
