package com.t1.aopopenschool.controller;

import com.t1.aopopenschool.model.ExecutionTime;
import com.t1.aopopenschool.service.ExampleService;
import com.t1.aopopenschool.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class TrackTimeController {
    private final TrackingService trackingService;
    private final ExampleService exampleService;

    @GetMapping("/statistics/{methodName}")
    public List<ExecutionTime> getExecutionTimeLogs(@PathVariable String methodName) {
        return trackingService.getExecutionTimes(methodName);
    }

    @GetMapping("/average/{methodName}")
    public Map<String, Double> getAverage(@PathVariable String methodName) {
        return Map.of("average", trackingService.getAverageExecutionTime(methodName));
    }

    @GetMapping("/compute-primes-sync")
    public String computePrimesSync() {
        exampleService.computePrimesSync();
        return "Synchronous prime number computation executed";
    }

    @GetMapping("/compute-primes-async")
    public String computePrimesAsync() {
        exampleService.computePrimesAsync();
        return "Asynchronous prime number computation executed";
    }

    @GetMapping("/process-array-sync")
    public String processArraySync() {
        exampleService.processLargeArraySync();
        return "Synchronous array processing executed";
    }

    @GetMapping("/process-array-async")
    public String processArrayAsync() {
        exampleService.processLargeArrayAsync();
        return "Asynchronous array processing executed";
    }
}

