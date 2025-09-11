package com.zonatech.app.application.usecases.entrevistaEstudiante;

import com.zonatech.app.domain.exceptions.EntrevistaEstudianteNoEncontradoException;
import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.EvaluarEntrevistaEstudianteUseCase;
import com.zonatech.app.domain.ports.output.EntrevistaEstudianteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
public class EvaluarEntrevistaEstudianteUseCaseImpl implements EvaluarEntrevistaEstudianteUseCase {

    private final EntrevistaEstudianteRepositoryPort repositoryPort;

    @Override
    public void evaluarEntrevistaEstudiante(Long idEntrevista, int valoracion, String feedback) {

        if (valoracion <= 0 || idEntrevista == null || feedback.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parametros no validos");
        }
        EntrevistaEstudiante entrevistaEstudiante = repositoryPort.findById(idEntrevista)
                .orElseThrow(() -> new EntrevistaEstudianteNoEncontradoException("Entrevista no encontrada con id : " + idEntrevista));
        entrevistaEstudiante.setFeedBack(feedback);
        entrevistaEstudiante.setValoracion(valoracion);
        repositoryPort.save(entrevistaEstudiante);
    }
}
