package br.com.alura.screensound.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaGoogleGemini {
    private static String GOOGLE_API_KEY = System.getenv("GEMINI_KEY");

    public static String consulta(String texto) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GOOGLE_API_KEY))
                .POST(HttpRequest.BodyPublishers.ofString("{\n  \"contents\": [{\n    \"parts\":[{\"text\": \"Faça uma breve descrição sobre: " + texto + "\"}]\n    }]\n   }"))
                .setHeader("Content-Type", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return pegaResposta(response.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static String pegaResposta(String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(body);
            return rootNode.at("/candidates").get(0).at("/content").at("/parts").get(0).path("text").asText();
        } catch (IOException e) {
            System.err.println("Erro ao processar o JSON: " + e.getMessage());
        }
        return null;
    }

}


