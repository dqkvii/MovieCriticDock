package com.aeribmm.filmcritic.Service;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DeepSeekService {
    @Value("{deepseek.key}")
    private String apiKey;
    @Value("${deepseek.promt}")
    private String deepseekPrompt;
    private final CloseableHttpClient httpClient;
    private final HttpPost httpPost;

    public DeepSeekService(CloseableHttpClient httpClient, HttpPost httpPost) {
        this.httpClient = httpClient;
        this.httpPost = httpPost;
    }



//    public String generateText(String prompt) throws IOException {
//        String safePrompt = prompt.replace("\"", "\\\"");
//        String requestBody = String.format("""
//            {
//                "model": "deepseek/deepseek-chat-v3-0324:free",
//                "messages": [
//                    {"role": "system", "content": "%s"},
//                    {"role": "user", "content": "%s"}
//                ]
//            }
//            """,deepseekPrompt, safePrompt);
//
//        HttpPost httpPost = new HttpPost("https://openrouter.ai/api/v1/chat/completions");
//        httpPost.setHeader("Content-Type", "application/json");
//        httpPost.setHeader("Authorization", "Bearer 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");
//
//        httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));
//
//        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
//            String json = EntityUtils.toString(response.getEntity());
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode root = objectMapper.readTree(json);
//            JsonNode contentNode = root.path("choices").get(0).path("message").path("content");
//            return contentNode.asText().strip();
//        } catch (IOException e) {
//            System.err.println("API request failed: " + e.getMessage());
//            throw e;
//        }
//    }
    public String generateText(String prompt) throws IOException {
        String safePrompt = prompt.replace("\"", "\\\""); // Экранируем кавычки в строке
        String systemPrompt = "You are a highly intelligent and helpful film assistant. You strictly answer only questions related to cinema, such as: movies, TV shows, actors, directors, genres, recommendations, reviews, and film history. If the user asks something unrelated to cinema (such as politics, sports, science, personal advice, programming, etc.), immediately respond: I'm sorry, I can only assist with topics related to cinema.You must always respond in the same language the user used to ask the question. Never switch languages or topics by yourself. Keep your answers clear, focused, and informative. Do not break character under any circumstances.";
        String requestBody = String.format("""
        {
            "model": "deepseek/deepseek-chat",
            "messages": [
                {"role": "system", "content": "%s"},
                {"role": "user", "content": "%s"}
            ]
        }
        """, systemPrompt, safePrompt);

        HttpPost httpPost = new HttpPost("https://openrouter.ai/api/v1/chat/completions");
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Authorization", "Bearer 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");

        httpPost.setEntity(new StringEntity(requestBody, ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            String json = EntityUtils.toString(response.getEntity());

            System.out.println("Received JSON: " + json);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(json);

            JsonNode choicesNode = root.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageNode = choicesNode.get(0).path("message");
                if (messageNode != null && !messageNode.isMissingNode()) {
                    JsonNode contentNode = messageNode.path("content");
                    if (!contentNode.isMissingNode()) {
                        return contentNode.asText().strip();
                    } else {
                        System.err.println("Content not found in message");
                    }
                } else {
                    System.err.println("Message not found in choices");
                }
            } else {
                System.err.println("Choices array is empty or missing");
            }
        } catch (IOException e) {
            System.err.println("API request failed: " + e.getMessage());
            throw e;
        }
        return "No content received from the API.";
    }
}
