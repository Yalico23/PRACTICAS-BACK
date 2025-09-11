package com.zonatech.app.domain.ports.output;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EntrevistaRepositoryPort {
    Entrevista save(Entrevista entrevista);
    Optional<Entrevista> findById(Long id);
    void deleteById(Long id);
    Page<Entrevista> findByMentorIdWithFilter(String email, String filter, Pageable pageable);
    Page<EntrevistaResumen> EntrevistaResumen(String emailEstudiante,String filter, Pageable pageable);
    List<Entrevista> findByMentorEmail(String email);
}