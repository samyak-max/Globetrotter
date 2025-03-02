package com.headout.ai_service.services;

import com.headout.ai_service.config.CityListConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenerateCitiesData {

    private final String[] cityCountryList;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String apiKey;

    @Autowired
    public GenerateCitiesData(CityListConfig cityListConfig, RestTemplate restTemplate) {
        this.cityCountryList = cityListConfig.cityCountryList();
        this.objectMapper = new ObjectMapper();
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> generateCityData() {
        return Arrays.stream(cityCountryList)
                .map(this::generateCityDataMap)
                .collect(Collectors.toList());
    }

    private Map<String, Object> generateCityDataMap(String cityCountry) {
        String[] parts = cityCountry.split(", ");
        String city = parts[0];
        String country = parts[1];

        Map<String, Object> cityData = new HashMap<>();
        cityData.put("city", city);
        cityData.put("country", country);
        cityData.put("clues", generateData(city, "clues", 2));
        cityData.put("fun_facts", generateData(city, "fun facts", 2));
        cityData.put("trivia", generateData(city, "trivia", 2));

        return cityData;
    }

    private List<String> generateData(String city, String dataType, int count) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String prompt = String.format("Generate %d interesting %s about %s. Only give the answer in points, that are on different lines. The output should be just plain text, separated by newline.", count, dataType, city);
        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", prompt);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String content = rootNode.path("candidates").path(0).path("content").path("parts").path(0).path("text").asText()
            .replaceAll("[*\\\\\\\\^/\\-]", "")
            .strip();;

            return Arrays.stream(content.split("\n"))
                    .map(String::trim)
                    .filter(item -> !item.isEmpty())
                    .limit(count)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            System.err.println("Error parsing JSON response: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
