package com.headout.ai_service.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "/cities", produces = "application/json")
@CrossOrigin(origins = "*")
public class CityListController {

    private final String[] cityCountryList;

    public CityListController(String[] cityCountryList) {
        this.cityCountryList = cityCountryList;
    }

    @GetMapping
    public List<Map<String, String>> getCities() {
        List<Map<String, String>> cities = new ArrayList<>();

        for (String entry : cityCountryList) {
            String[] parts = entry.split(", ");
            if (parts.length == 2) {
                Map<String, String> cityData = new HashMap<>();
                cityData.put("city", parts[0]);
                cityData.put("country", parts[1]);
                cities.add(cityData);
            }
        }

        return cities;
    }
}