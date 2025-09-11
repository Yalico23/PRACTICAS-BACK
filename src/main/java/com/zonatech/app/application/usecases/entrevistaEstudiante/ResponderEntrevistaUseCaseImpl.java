package com.zonatech.app.application.usecases.entrevistaEstudiante;

import com.zonatech.app.domain.models.EntrevistaEstudiante;
import com.zonatech.app.domain.models.EntrevistaRef;
import com.zonatech.app.domain.models.UsuarioRef;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.ResponderEntrevistaUseCase;
import com.zonatech.app.domain.ports.output.AwsServiceExternalServicePort;
import com.zonatech.app.domain.ports.output.EntrevistaEstudianteRepositoryPort;
import com.zonatech.app.domain.ports.output.IAServiceExternalServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ResponderEntrevistaUseCaseImpl implements ResponderEntrevistaUseCase {

    private final AwsServiceExternalServicePort externalServicePort;
    private final EntrevistaEstudianteRepositoryPort repositoryPort;
    private final String bucketName;
    private final IAServiceExternalServicePort servicePort;

    // En vez de usar multipart file uso byte para no perder datos, una vez si funciona con multipart, mas de una vez habra problemas de perdida de memoria

    @Override
    public void responderEntrevista(Long idEntrevista, Long idUsuario, MultipartFile video) {

        try {
            byte[] videoBytes = video.getBytes();

            String keyVideo = uploadVideo(videoBytes, video.getOriginalFilename());
            String videoResumen = transcribeVideo(videoBytes, video.getOriginalFilename());

            EntrevistaEstudiante entrevistaEstudiante = new EntrevistaEstudiante();
            entrevistaEstudiante.setFechaEntrevista(LocalDate.now());
            entrevistaEstudiante.setEstudiante(new UsuarioRef(idUsuario));
            entrevistaEstudiante.setEntrevista(new EntrevistaRef(idEntrevista));
            entrevistaEstudiante.setCompletado(true);
            entrevistaEstudiante.setURLVideo(keyVideo);
            entrevistaEstudiante.setVideoResumen(videoResumen);
            repositoryPort.save(entrevistaEstudiante);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo de video", e);
        }
    }

    private String uploadVideo(byte[] videoBytes, String originalFilename) {
        File tempFile = null;
        try {
            // Generar key única para S3
            String key = "entrevista/estudiante/" + UUID.randomUUID() + ".webm";

            // Crear archivo temporal con los bytes
            tempFile = File.createTempFile("video-", ".webm");
            Files.write(tempFile.toPath(), videoBytes);

            // Subir a S3 usando el puerto
            boolean uploaded = externalServicePort.uploadFileS3(bucketName, key, tempFile.toPath());
            if (!uploaded) {
                throw new RuntimeException("Error al subir el video a S3");
            }
            return key;

        } catch (IOException e) {
            throw new RuntimeException("Error al procesar el archivo de video para S3", e);
        } finally {
            if (tempFile != null && tempFile.exists()) {
                if (tempFile.delete()) {
                    log.debug("Archivo temporal eliminado: {}", tempFile.getAbsolutePath());
                } else {
                    log.warn("No se pudo eliminar el archivo temporal: {}", tempFile.getAbsolutePath());
                }
            }
        }
    }

    private String transcribeVideo(byte[] videoBytes, String originalFilename) {
        try {
            return servicePort.transcribeVideo(videoBytes, originalFilename);
        } catch (Exception e) {
            throw new RuntimeException("Error al transcribir video", e);
        }
    }
}
