package com.zonatech.app.domain.ports.input.entrevista;

import com.zonatech.app.domain.models.Entrevista;
import com.zonatech.app.domain.models.EntrevistaResumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ListarEntrevistasUseCase {
    Page<Entrevista> findEntrevistasByMentorIdPageable(String email, String filtro, Pageable pageable);
    Entrevista findEntrevistaById(Long id);
    Page<EntrevistaResumen> listarEntrevistasEstudiante(String email, String filtro, Pageable pageable);
    List<Entrevista> listByMentorEmail(String email);
}
