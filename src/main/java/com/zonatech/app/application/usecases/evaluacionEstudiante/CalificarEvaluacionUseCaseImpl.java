package com.zonatech.app.application.usecases.evaluacionEstudiante;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonatech.app.domain.models.AnalisisRespuestasTextoRequest;
import com.zonatech.app.domain.models.EvaluacionEstudiante;
import com.zonatech.app.domain.ports.input.evaluacionEstudiante.CalificarEvaluacionUseCase;
import com.zonatech.app.domain.ports.output.EvaluacionEstudianteRespositoryPort;
import com.zonatech.app.domain.ports.output.IAServiceExternalServicePort;
import com.zonatech.app.infrastructure.dto.request.evaluacionEstudiante.DtoRequestEvaluarEvaluacion;
import com.zonatech.app.infrastructure.dto.response.IA.AnalisisRespuestasTextoResponse;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CalificarEvaluacionUseCaseImpl implements CalificarEvaluacionUseCase {

    private final EvaluacionEstudianteRespositoryPort estudianteRespositoryPort;
    private final IAServiceExternalServicePort externalServicePort;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AnalisisRespuestasTextoResponse analizarRespuestasTexto(AnalisisRespuestasTextoRequest request) {
        try {
            // nota : analisis de respuestas de texto uno a uno
            List<AnalisisRespuestasTextoResponse.AnalisisIA> analisisResultados = new ArrayList<>();

            for (AnalisisRespuestasTextoRequest.RespuestaTextoIA respuesta : request.getRespuestasTexto()) {
                try {
                    AnalisisRespuestasTextoResponse.AnalisisIA analisis = analizarRespuestaIndividual(respuesta);
                    analisisResultados.add(analisis);
                } catch (Exception e) {
                    analisisResultados.add(crearAnalisisFallback(respuesta));
                }
            }

            return AnalisisRespuestasTextoResponse.builder()
                    .success(true)
                    .data(AnalisisRespuestasTextoResponse.AnalisisData.builder()
                            .respuestasAnalizadas(analisisResultados)
                            .build())
                    .message("Análisis completado exitosamente")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el análisis con IA: " + e.getMessage());
        }
    }

    private AnalisisRespuestasTextoResponse.AnalisisIA crearAnalisisFallback(AnalisisRespuestasTextoRequest.RespuestaTextoIA respuesta) {
        return AnalisisRespuestasTextoResponse.AnalisisIA.builder()
                .preguntaId(respuesta.getPreguntaId())
                .notaSugerida(0)
                .notaMaxima(respuesta.getValorMaximo())
                .porcentajeAcierto(0.0)
                .requiereRevisionManual(true)
                .comentarios(AnalisisRespuestasTextoResponse.ComentariosIA.builder()
                        .comentarioGeneral("Error en el análisis automático. Requiere evaluación manual.")
                        .fortalezas(List.of("Requiere revisión manual"))
                        .debilidades(List.of("No se pudo procesar automáticamente"))
                        .sugerencias(List.of("Evaluar manualmente esta respuesta"))
                        .build())
                .build();
    }

    private AnalisisRespuestasTextoResponse.AnalisisIA analizarRespuestaIndividual
            (AnalisisRespuestasTextoRequest.RespuestaTextoIA respuesta) {
        String prompt = construirPromptEvaluacion(respuesta);
        String respuestaIA = externalServicePort.llamarServicioIA(prompt);

        return parsearRespuestaIA(respuestaIA, respuesta);
    }

    private AnalisisRespuestasTextoResponse.AnalisisIA parsearRespuestaIA(String respuestaIA, AnalisisRespuestasTextoRequest.RespuestaTextoIA respuestaOriginal) {
        try {
            // Limpiar la respuesta (remover markdown si existe)
            String jsonLimpio = respuestaIA.trim();
            if (jsonLimpio.startsWith("```json")) {
                jsonLimpio = jsonLimpio.substring(7);
            }
            if (jsonLimpio.endsWith("```")) {
                jsonLimpio = jsonLimpio.substring(0, jsonLimpio.length() - 3);
            }
            jsonLimpio = jsonLimpio.trim();

            JsonNode jsonNode = objectMapper.readTree(jsonLimpio);

            return AnalisisRespuestasTextoResponse.AnalisisIA.builder()
                    .preguntaId(respuestaOriginal.getPreguntaId())
                    .notaSugerida(jsonNode.get("notaSugerida").asInt())
                    .notaMaxima(respuestaOriginal.getValorMaximo())
                    .porcentajeAcierto(jsonNode.get("porcentajeAcierto").asDouble())
                    .requiereRevisionManual(jsonNode.get("requiereRevisionManual").asBoolean())
                    .comentarios(AnalisisRespuestasTextoResponse.ComentariosIA.builder()
                            .comentarioGeneral(jsonNode.get("comentarioGeneral").asText())
                            .fortalezas(convertirArrayALista(jsonNode.get("fortalezas")))
                            .debilidades(convertirArrayALista(jsonNode.get("debilidades")))
                            .sugerencias(convertirArrayALista(jsonNode.get("sugerencias")))
                            .build())
                    .build();

        } catch (Exception e) {
            return crearAnalisisFallback(respuestaOriginal);
        }
    }

    private List<String> convertirArrayALista(JsonNode arrayNode) {
        List<String> lista = new ArrayList<>();
        if (arrayNode != null && arrayNode.isArray()) {
            arrayNode.forEach(node -> lista.add(node.asText()));
        }
        return lista;
    }

    private String construirPromptEvaluacion(AnalisisRespuestasTextoRequest.RespuestaTextoIA respuesta) {

        StringBuilder prompt = new StringBuilder();

        prompt.append("Eres un profesor experto evaluando respuestas de estudiantes. ");
        prompt.append("Analiza la siguiente respuesta y proporciona una evaluación detallada.\n\n");

        prompt.append("PREGUNTA: ").append(respuesta.getPregunta()).append("\n\n");

        prompt.append("RESPUESTA DEL ESTUDIANTE: ").append(respuesta.getRespuestaEstudiante()).append("\n\n");

        prompt.append("VALOR MÁXIMO DE LA PREGUNTA: ").append(respuesta.getValorMaximo()).append(" puntos\n\n");

        if (respuesta.getCriteriosEvaluacion() != null && !respuesta.getCriteriosEvaluacion().isEmpty()) {
            prompt.append("CRITERIOS DE EVALUACIÓN: ").append(respuesta.getCriteriosEvaluacion()).append("\n\n");
        }

        prompt.append("INSTRUCCIONES:\n");
        prompt.append("1. Evalúa la respuesta considerando: precisión, completitud, claridad y relevancia\n");
        prompt.append("2. Asigna una calificación entre 0 y ").append(respuesta.getValorMaximo()).append(" puntos\n");
        prompt.append("3. Proporciona retroalimentación constructiva\n");
        prompt.append("4. Indica si requiere revisión manual adicional\n\n");

        prompt.append("FORMATO DE RESPUESTA (debe ser JSON válido):\n");
        prompt.append("{\n");
        prompt.append("  \"notaSugerida\": [número entre 0 y ").append(respuesta.getValorMaximo()).append("],\n");
        prompt.append("  \"porcentajeAcierto\": [porcentaje de 0 a 100],\n");
        prompt.append("  \"comentarioGeneral\": \"[evaluación general en 2-3 líneas]\",\n");
        prompt.append("  \"fortalezas\": [\"fortaleza1\", \"fortaleza2\"],\n");
        prompt.append("  \"debilidades\": [\"debilidad1\", \"debilidad2\"],\n");
        prompt.append("  \"sugerencias\": [\"sugerencia1\", \"sugerencia2\"],\n");
        prompt.append("  \"requiereRevisionManual\": [true/false]\n");
        prompt.append("}\n\n");

        prompt.append("IMPORTANTE: Responde ÚNICAMENTE con el JSON, sin texto adicional.");

        return prompt.toString();
    }

    @Override
    public EvaluacionEstudiante calificarEvaluacion(DtoRequestEvaluarEvaluacion dto) {

        EvaluacionEstudiante evaluacionEstudiante = estudianteRespositoryPort
                .findById(dto.getIdEvaluacion())
                .orElseThrow(() ->
                        new RuntimeException("Evaluación no encontrada con el ID: " + dto.getIdEvaluacion()));

        evaluacionEstudiante.setFeedback(dto.getFeedback());
        evaluacionEstudiante.setCalificacionFinal(dto.getNotaFinal());
        // Convertimos lista de notas en un mapa por idPregunta
        Map<Long, Integer> notasMap = dto.getNotasPreguntas().stream()
                .collect(Collectors.toMap(
                        DtoRequestEvaluarEvaluacion.DtoNotaPregunta::getIdPregunta,
                        DtoRequestEvaluarEvaluacion.DtoNotaPregunta::getNotaPregunta
                ));

        evaluacionEstudiante.getRespuestaEstudiantes().forEach(respuesta -> {
            Long idPregunta = respuesta.getPregunta().getId();
            if (notasMap.containsKey(idPregunta)) {
                respuesta.setNota(notasMap.get(idPregunta));
            }
        });

        return estudianteRespositoryPort.save(evaluacionEstudiante);
    }
}
