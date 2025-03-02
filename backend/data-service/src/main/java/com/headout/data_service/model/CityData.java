package com.headout.data_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "city_data")
public class CityData {

    @Id
    private String id;
    private String city;
    private String country;

    @JsonProperty("fun_facts") // Maps JSON "fun_facts" to this field
    private List<String> funFacts;

    @JsonProperty("trivia") // Ensures correct mapping
    private List<String> trivia;

    @JsonProperty("clues") // Ensures correct mapping
    private List<String> clues;

    public CityData() {}

    public CityData(String city, String country, List<String> funFacts, List<String> trivia, List<String> clues) {
        this.city = city;
        this.country = country;
        this.funFacts = funFacts;
        this.trivia = trivia;
        this.clues = clues;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public List<String> getFunFacts() { return funFacts; }
    public void setFunFacts(List<String> funFacts) { this.funFacts = funFacts; }

    public List<String> getTrivia() { return trivia; }
    public void setTrivia(List<String> trivia) { this.trivia = trivia; }

    public List<String> getClues() { return clues; }
    public void setClues(List<String> clues) { this.clues = clues; }
}
