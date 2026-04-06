package com.vincent.resumeanalyzer.ai;

import com.vincent.resumeanalyzer.dto.ResumeAnalysisResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ResumeAnalysisResponse analyzeResume(String resumeText) throws Exception {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        String requestBody = """
                {
                  "contents": [{
                    "parts": [{
                      "text": "Analyze this resume and return ONLY VALID JSON.\s
                               DO NOT add explanation, markdown, or extra text.
                
                               Format:
                               {
                                 \\"skills\\": [],
                                 \\"experience_level\\": \\"\\",
                                 \\"strengths\\": [],
                                 \\"suggestions\\": [],
                                 \\"job_fit_score\\": 0
                               }
                
                               Rules:
                               - job_fit_score must be between 50 and 95
                               - strengths and suggestions must be clean bullet points
                               - no markdown, no formatting symbols
                
                               Resume: %s"
                    }]
                  }]
                }
                """.formatted(resumeText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        Map<String, Object> response = restTemplate.postForObject(url, entity, Map.class);

        // ✅ NULL & STRUCTURE CHECK (IMPORTANT)
        if (response == null || !response.containsKey("candidates")) {
            throw new RuntimeException("Invalid AI response");
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
        if (candidates.isEmpty()) {
            throw new RuntimeException("No candidates returned from AI");
        }

        Map<String, Object> candidate = candidates.get(0);
        Map<String, Object> content = (Map<String, Object>) candidate.get("content");
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

        if (parts == null || parts.isEmpty()) {
            throw new RuntimeException("Invalid AI content format");
        }

        String text = (String) parts.get(0).get("text");

        // ✅ CLEAN RESPONSE
        text = text.replace("```json", "")
                .replace("```", "")
                .replaceAll("\\*\\*", "")
                .trim();

        try {
            return objectMapper.readValue(text, ResumeAnalysisResponse.class);
        } catch (Exception e) {
            System.out.println("Parsing failed: " + text);
            throw e;
        }
    }
}