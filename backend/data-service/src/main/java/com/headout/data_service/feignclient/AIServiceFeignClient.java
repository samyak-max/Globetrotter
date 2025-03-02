package com.headout.data_service.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "ai-service", url = "${ai-service.url}")
public interface AIServiceFeignClient {

    @GetMapping("/ai/generate")
    List<Map<String, Object>> fetchGeneratedCityData();
}
