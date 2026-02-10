package com.chitkara.bfhlapi.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String askAi(String question) {
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

            RestTemplate restTemplate = new RestTemplate();

            // Build request body
            Map<String, Object> part = new HashMap<>();
            part.put("text", question + ". Answer in one word only.");

            Map<String, Object> content = new HashMap<>();
            content.put("parts", List.of(part));

            Map<String, Object> body = new HashMap<>();
            body.put("contents", List.of(content));

            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            Map responseBody = response.getBody();

            // Parse response safely
            List candidates = (List) responseBody.get("candidates");
            Map first = (Map) candidates.get(0);
            Map contentMap = (Map) first.get("content");
            List parts = (List) contentMap.get("parts");
            Map textPart = (Map) parts.get(0);

            String text = (String) textPart.get("text");

            // Return only first word (as required)
            return text.trim().split("\\s+")[0];

        } catch (Exception e) {
            e.printStackTrace();  // show real error in console
            return "Error";
        }
    }
}
