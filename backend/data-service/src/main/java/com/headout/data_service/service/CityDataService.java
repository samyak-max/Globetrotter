package com.headout.data_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.headout.data_service.feignclient.AIServiceFeignClient;
import com.headout.data_service.model.CityData;
import com.headout.data_service.repository.CityDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CityDataService {

    private final AIServiceFeignClient aiServiceFeignClient;
    private final CityDataRepository cityDataRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public CityDataService(AIServiceFeignClient aiServiceFeignClient, CityDataRepository cityDataRepository) {
        this.aiServiceFeignClient = aiServiceFeignClient;
        this.cityDataRepository = cityDataRepository;
        this.objectMapper = new ObjectMapper();
    }

    public void populateCityData() {
        List<Map<String, Object>> generatedData = aiServiceFeignClient.fetchGeneratedCityData();
        if (generatedData == null || generatedData.isEmpty()) {
            System.err.println("No data received from AI service.");
            return;
        }
        System.out.println("Received " + generatedData.size() + " city entries.");
        for (Map<String, Object> cityDataMap : generatedData) {
            System.out.println("Processing city data: " + cityDataMap);

            try {
                CityData cityData = convertToCityData(cityDataMap);

                if (cityData.getCity() == null || cityData.getCountry() == null) {
                    System.err.println("Skipping invalid data: " + cityDataMap);
                    continue;
                }

                // Remove old entry (if exists)
                cityDataRepository.deleteByCityAndCountry(cityData.getCity(), cityData.getCountry());
                System.out.println("Deleted old data (if existed) for: " + cityData.getCity() + ", " + cityData.getCountry());

                // Save new data
                cityDataRepository.save(cityData);
                System.out.println("Saved updated data for: " + cityData.getCity() + ", " + cityData.getCountry());

            } catch (Exception e) {
                System.err.println("Error processing city data: " + e.getMessage());
            }
        }
    }

    public Map<String, Object> getCityData(String city) {
        Optional<CityData> cityDataOpt = cityDataRepository.findByCity(city);

        if (cityDataOpt.isEmpty()) {
            throw new RuntimeException("City data not found for: " + city);
        }

        CityData cityData = cityDataOpt.get();

        // Convert CityData to a structured response
        Map<String, Object> response = new HashMap<>();
        response.put("city", cityData.getCity());
        response.put("country", cityData.getCountry());
        response.put("clues", cityData.getClues());
        response.put("fun_facts", cityData.getFunFacts());
        response.put("trivia", cityData.getTrivia());

        return response;
    }

    private CityData convertToCityData(Map<String, Object> cityDataMap) {
        return objectMapper.convertValue(cityDataMap, CityData.class);
    }
}
