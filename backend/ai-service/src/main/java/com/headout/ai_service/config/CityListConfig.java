package com.headout.ai_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CityListConfig {

    @Bean
    public String[] cityCountryList() {
        return new String[] {
            "New York, USA",
            "Los Angeles, USA",
            "Chicago, USA",
            "Houston, USA",
            "Phoenix, USA",
            "London, UK",
            "Paris, France",
            "Tokyo, Japan",
            "Sydney, Australia",
            "Berlin, Germany",
            "Mexico City, Mexico",
            "Rio de Janeiro, Brazil",
            "Madrid, Spain",
            "Buenos Aires, Argentina",
            "Rome, Italy",
            "Toronto, Canada",
            "Dubai, UAE",
            "Bangkok, Thailand",
            "Cairo, Egypt",
            "Moscow, Russia",
            "Mumbai, India",
            "Shanghai, China",
            "Cape Town, South Africa",
            "Barcelona, Spain",
            "Seoul, South Korea",
            "Lagos, Nigeria",
            "Hong Kong, China",
            "Singapore, Singapore",
            "São Paulo, Brazil",
            "Kuala Lumpur, Malaysia",
            "Istanbul, Turkey",
            "Jakarta, Indonesia",
            "Vienna, Austria",
            "Zurich, Switzerland",
            "Melbourne, Australia",
            "New Delhi, India",
            "Amsterdam, Netherlands",
            "Beijing, China"
        };
    }
}
