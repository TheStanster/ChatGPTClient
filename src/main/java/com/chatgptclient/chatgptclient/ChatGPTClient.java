package com.chatgptclient.chatgptclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Map;

public class ChatGPTClient {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final String apiKey;

    public ChatGPTClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String complete(String prompt) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        HttpEntity<String> request = new HttpEntity<>(prompt, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());
        JsonNode messageNode = rootNode.path("choices").get(0).path("message");
        String content = messageNode.path("content").asText();

        return content;
    }
}
