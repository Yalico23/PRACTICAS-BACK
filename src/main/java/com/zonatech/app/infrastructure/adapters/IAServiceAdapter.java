package com.zonatech.app.infrastructure.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zonatech.app.domain.ports.output.IAServiceExternalServicePort;
import com.zonatech.app.infrastructure.dto.request.IA.IARequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class IAServiceAdapter implements IAServiceExternalServicePort {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.ia.api.model}")
    private String iaModel;
    @Value("${openai.ia.api.url}")
    private String iaApiUrl;
    @Value("${openai.ia.api.key}")
    private String iaApiKey;

    @Override
    public String llamarServicioIA(String prompt) {
        try {
            IARequest iaRequest = IARequest.builder()
                    .model(iaModel)
                    .messages(List.of(
                            new IARequest.IAMessage("user", prompt)
                    ))
                    .build();

            String response = webClient.post()
                    .uri(iaApiUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + iaApiKey)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(iaRequest)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Parsear la respuesta para extraer el contenido

            JsonNode jsonResponse = objectMapper.readTree(response);

            if (jsonResponse.has("choices") && !jsonResponse.get("choices").isEmpty()) {
                return jsonResponse.get("choices").get(0).get("message").get("content").asText();
            } else if (jsonResponse.has("error")) {
                throw new RuntimeException("Error de la IA: " + jsonResponse.get("error").asText());
            } else {
                throw new RuntimeException("Respuesta inesperada de la IA");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error en la comunicación con el servicio de IA: " + e.getMessage());
        }
    }

    @Value("${openai.whisper.api.url}")
    private String urlWhisper;

    @Override
    public String transcribeVideo(byte[] videoBytes, String filename) {
        log.info("Iniciando transcripción de video. Archivo: {}, Tamaño: {} bytes", filename, videoBytes.length);

        try {
            // Validar los bytes
            if (videoBytes == null || videoBytes.length == 0) {
                throw new RuntimeException("El archivo está vacío");
            }

            // Preparar el multipart data para el servicio local de Whisper
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();

            // Agregar el archivo de audio con el nombre correcto esperado por el servicio local
            parts.add("audio_file", new ByteArrayResource(videoBytes) {
                @Override
                public String getFilename() {
                    return filename != null ? filename : "video.webm";
                }
            });

            String response = webClient.post()
                    .uri(urlWhisper)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(parts))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError(), clientResponse -> {
                        log.error("Error 4xx en Whisper local. Status: {}", clientResponse.statusCode());
                        return clientResponse.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("Respuesta de error: {}", body);
                                    return new RuntimeException("Error del cliente en Whisper local: " + body);
                                });
                    })
                    .onStatus(status -> status.is5xxServerError(), clientResponse -> {
                        log.error("Error 5xx en Whisper local. Status: {}", clientResponse.statusCode());
                        return clientResponse.bodyToMono(String.class)
                                .map(body -> {
                                    log.error("Respuesta de error del servidor: {}", body);
                                    return new RuntimeException("Error del servidor en Whisper local: " + body);
                                });
                    })
                    .bodyToMono(String.class)
                    .block();

            log.info("Transcripción completada exitosamente. Longitud del texto: {} caracteres",
                    response != null ? response.length() : 0);

            return response;

        } catch (WebClientResponseException e) {
            log.error("Error de WebClient al transcribir audio. Status: {}, Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw new RuntimeException("Error en el servicio local de Whisper: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            log.error("Error inesperado al transcribir audio", e);
            throw new RuntimeException("Error al transcribir audio: " + e.getMessage(), e);
        }
    }
}
