package com.zonatech.app.application.usecases.entrevistaEstudiante;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zonatech.app.domain.models.ResponseEntrevistaEstudianteIA;
import com.zonatech.app.domain.ports.input.entrevistaEstudiante.GenerarResumenIAUseCase;
import com.zonatech.app.domain.ports.output.IAServiceExternalServicePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class GenerarResumenIAUseCaseImpl implements GenerarResumenIAUseCase {

    private final IAServiceExternalServicePort servicePort;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResponseEntrevistaEstudianteIA generarResumenIA(String resumen) {
        String promptCompleto = generarPrompt(resumen);
        String respuestaIA = servicePort.llamarServicioIA(promptCompleto);
        String respuestaLimpia = limpiarRespuesta(respuestaIA);
        return parsearRespuestaEstructurada(respuestaLimpia);
    }

    private String generarPrompt(String resumen) {

        return "Eres un evaluador experto en entrevistas técnicas estudiantiles.\n\n" +
                "IMPORTANTE: Responde ÚNICAMENTE con un objeto JSON válido con esta estructura exacta.\n" +
                "CADA CAMPO DEBE TENER CONTENIDO ESPECÍFICO Y ÚTIL:\n\n" +
                "{\n" +
                "  \"notaSugerida\": [nota entre 1 y 20 segun lo que merezca],\n" +
                "  \"analisis\": \"[evaluación general concisa del desempeño en 2-3 oraciones]\",\n" +
                "  \"fortalezas\": \"[enumera 2-3 fortalezas específicas observadas, separadas por puntos]\",\n" +
                "  \"debilidades\": \"[identifica 2-3 áreas de mejora específicas, separadas por puntos]\",\n" +
                "  \"recomendaciones\": \"[da 2-3 sugerencias concretas y accionables para mejorar]\"\n" +
                "}\n\n" +
                "INSTRUCCIONES ESPECÍFICAS:\n" +
                "- Analiza TODOS los aspectos: técnicos, comunicación, actitud, preparación\n" +
                "- En 'fortalezas': menciona qué hizo bien el candidato específicamente\n" +
                "- En 'debilidades': identifica áreas concretas que necesita mejorar\n" +
                "- En 'recomendaciones': da consejos prácticos y específicos\n" +
                "- Sé constructivo pero honesto en tu evaluación\n" +
                "- Si no hay información suficiente en algún aspecto, infiere basándote en el contexto\n\n" +
                "RESUMEN DE LA ENTREVISTA:\n" +
                "\"" + resumen + "\"\n\n" +
                "GENERA EL JSON COMPLETO CON TODAS LAS SECCIONES LLENAS:";
    }

    private String limpiarRespuesta(String respuesta) {
        if (respuesta == null) {
            return "";
        }
        return respuesta
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    private ResponseEntrevistaEstudianteIA parsearRespuestaEstructurada(String respuesta) {
        try {
            return objectMapper.readValue(respuesta, ResponseEntrevistaEstudianteIA.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
