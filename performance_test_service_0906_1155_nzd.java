// 代码生成时间: 2025-09-06 11:55:41
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/performance")
@Service
public class PerformanceTestService {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceTestService.class);
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    
    @Autowired
    public PerformanceTestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.executorService = Executors.newFixedThreadPool(10);
    }
    
    @GetMapping("/test")
    public ResponseEntity<?> performPerformanceTest() {
        try {
            List<Future<PerformanceResult>> futures = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                // Submit performance test tasks to the executor service
                Future<PerformanceResult> future = executorService.submit(() -> {
                    long startTime = System.currentTimeMillis();
                    // Simulate a REST API call
                    restTemplate.getForObject("https://api.example.com/data", String.class);
                    long endTime = System.currentTimeMillis();
                    return new PerformanceResult(endTime - startTime);
                });
                futures.add(future);
            }
            
            // Wait for all tasks to complete and collect results
            List<Double> times = new ArrayList<>();
            for (Future<PerformanceResult> future : futures) {
                times.add(future.get().getResponseTime());
            }
            
            return ResponseEntity.ok().body(times);
        } catch (Exception e) {
            logger.error("Error during performance test", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during performance test");
        }
    }
    
    // Inner class to hold performance test result
    private static class PerformanceResult {
        private double responseTime;
        
        public PerformanceResult(double responseTime) {
            this.responseTime = responseTime;
        }
        
        public double getResponseTime() {
            return responseTime;
        }
    }
}