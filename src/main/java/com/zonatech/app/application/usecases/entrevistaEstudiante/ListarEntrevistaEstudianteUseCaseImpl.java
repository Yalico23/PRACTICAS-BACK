package com.zonatech.app.application.usecases.entrevistaEstudiante;

import com.zonatech.app.domain.exceptions.EntrevistaEstudianteNoEncontradoException;
import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.ResponseDtoEntrevistaPendientes;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.ListarEntrevistaEstudianteUseCase;
import com.zonatech.app.domain.ports.output.AwsServiceExternalServicePort;
import com.zonatech.app.domain.ports.output.EntrevistaEstudianteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Duration;

@RequiredArgsConstructor
public class ListarEntrevistaEstudianteUseCaseImpl implements ListarEntrevistaEstudianteUseCase {

    private final EntrevistaEstudianteRepositoryPort repositoryPort;
    private final AwsServiceExternalServicePort awsServiceExternalServicePort;
    private final String bucketName;

    @Override
    public EntrevistaEstudiante findById(Long id) {

        EntrevistaEstudiante entrevistaEstudiante = repositoryPort.findById(id)
                .orElseThrow(() -> new EntrevistaEstudianteNoEncontradoException("EntrevistaEstudiante no encontrado con id :" + id));

        String urlPreFirmado = awsServiceExternalServicePort.getPresignedUrl
                (bucketName, entrevistaEstudiante.getURLVideo(), Duration.ofHours(1));

        entrevistaEstudiante.setURLVideo(urlPreFirmado);
        return entrevistaEstudiante;
    }

    @Override
    public Page<ResponseDtoEntrevistaPendientes> listEntrevistaEstudianteByIdEvaluacion(Long idEntrevista, String filter, Pageable pageable) {
        return repositoryPort.listEntrevistasPendientesByEvaluacionId
                (idEntrevista, filter, pageable);
    }


}
