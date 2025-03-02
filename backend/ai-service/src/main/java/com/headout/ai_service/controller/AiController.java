package com.headout.ai_service.controller;

import com.headout.ai_service.services.GenerateCitiesData;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/ai", produces="application/json")
@CrossOrigin(origins="*")
public class AiController {

    private final GenerateCitiesData generateCitiesData;

    public AiController(GenerateCitiesData generateCitiesData) {
        this.generateCitiesData = generateCitiesData;
    }

    @GetMapping("/generate")
    public List<Map<String, Object>> generateCityData() {
        System.out.println("Generating city data...");
        List<Map<String, Object>> generatedData = generateCitiesData.generateCityData();
        return generatedData;
    }
}
