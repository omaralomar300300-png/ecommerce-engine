package com.example.ecommerceengine.load;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoadDistributionController {

    @Value("${app.instance.name}")
    private String instanceName;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/api/load/instance")
    public Map<String, Object> getInstanceInfo() {
        Map<String, Object> response = new HashMap<>();

        response.put("instanceName", instanceName);
        response.put("serverPort", serverPort);
        response.put("message", "Request handled by " + instanceName);
        response.put("timestamp", LocalDateTime.now().toString());

        return response;
    }
}