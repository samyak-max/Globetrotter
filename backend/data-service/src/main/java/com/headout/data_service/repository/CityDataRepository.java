package com.headout.data_service.repository;

import com.headout.data_service.model.CityData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityDataRepository extends MongoRepository<CityData, String> {
    Optional<CityData> findByCity(String city);
    void deleteByCityAndCountry(String city, String country);
}
