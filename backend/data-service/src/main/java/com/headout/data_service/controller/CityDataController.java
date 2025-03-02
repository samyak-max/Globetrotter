package com.headout.data_service.controller;

import com.headout.data_service.service.CityDataService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/city-data")
@CrossOrigin(origins = "*")
public class CityDataController {

    private final CityDataService cityDataService;

    public CityDataController(CityDataService cityDataService) {
        this.cityDataService = cityDataService;
    }

    @GetMapping("/populate")
    public String populateData() {
        cityDataService.populateCityData();
        return "City data populated successfully!";
    }

    @GetMapping("/fetch")
    public Map<String, Object> getCityData(@RequestParam String city) {
        return cityDataService.getCityData(city);
    }
}
