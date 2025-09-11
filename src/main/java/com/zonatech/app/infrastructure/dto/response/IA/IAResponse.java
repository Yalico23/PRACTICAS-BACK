package com.zonatech.app.infrastructure.dto.response.IA;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IAResponse {
    private List<Choice> choices;
    private String error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice {
        private Message message;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Message {
            private String content;
        }
    }
}
