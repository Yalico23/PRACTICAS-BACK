package com.zonatech.app.domain.ports.input.entrevistaEstudiante;

import org.springframework.web.multipart.MultipartFile;

public interface ResponderEntrevistaUseCase {
    void responderEntrevista(Long idEntrevista, Long idUsuario, MultipartFile video);
}
