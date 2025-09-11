package com.zonatech.app.infrastructure.repository;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import com.zonatech.app.domain.ports.output.EntrevistaRepositoryPort;
import com.zonatech.app.infrastructure.entities.EntrevistaEntity;
import com.zonatech.app.infrastructure.mappers.EntrevistaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class EntrevistaEntityAdapter implements EntrevistaRepositoryPort {

    private final EntrevistaEntityRepository entrevistaEntityRepository;
    private final EntrevistaMapper entrevistaMapper;

    @Override
    public Entrevista save(Entrevista entrevista) {
        EntrevistaEntity entrevistaEntity = entrevistaMapper.toEntity(entrevista);
        return entrevistaMapper.toModel(
                entrevistaEntityRepository.save(entrevistaEntity)
        );
    }

    @Override
    public Optional<Entrevista> findById(Long id) {
        return entrevistaEntityRepository.findById(id)
                .map(entrevistaMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        entrevistaEntityRepository.deleteById(id);
    }

    @Override
    public Page<Entrevista> findByMentorIdWithFilter(String email, String filter, Pageable pageable) {
        return entrevistaEntityRepository.findByMentorIdWithFilter(email, filter, pageable)
                .map(entrevistaMapper::toModel);
    }

    @Override
    public Page<EntrevistaResumen> EntrevistaResumen(String emailEstudiante, String filter, Pageable pageable) {
        return entrevistaEntityRepository.findEntrevistasResumen(emailEstudiante, filter, pageable);
    }

    @Override
    public List<Entrevista> findByMentorEmail(String email) {
        return entrevistaMapper.toModel(entrevistaEntityRepository.findByMentorEmail(email));
    }
}
