package br.com.gahantognoli.front_gestao_vagas.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormatErrorMessage {

    public static String formatErrorMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(message);
            if (root.isArray()) {
                return formatArrayErrorMessage(root);
            }
            return root.asText();
        } catch (Exception e) {
            return message;
        }
    }

    public static String formatArrayErrorMessage(JsonNode arrayNode) {
        StringBuilder formattedMessage = new StringBuilder();
        for (JsonNode node : arrayNode) {
            formattedMessage
                .append("- ")
                .append(node.get("message").asText())
                .append("\n");
        }
        return formattedMessage.toString().trim();
    }

}
