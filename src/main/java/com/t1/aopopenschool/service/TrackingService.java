package com.t1.aopopenschool.service;

import com.t1.aopopenschool.model.ExecutionTime;
import com.t1.aopopenschool.repository.ExecutionTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingService {
    private final ExecutionTimeRepository repository;

    public void saveMethodExecutionTime(String className, String methodName, Long executionTime) {
        ExecutionTime time = new ExecutionTime();
        time.setClassName(className);
        time.setMethodName(methodName);
        time.setExecutionTime(executionTime);
        repository.save(time);
    }

    public List<ExecutionTime> getExecutionTimes(String methodName) {
        return repository.findByMethodNameIgnoreCase(methodName);
    }

    public double getAverageExecutionTime(String methodName) {
        List<ExecutionTime> times = getExecutionTimes(methodName);
        return times.stream()
                .mapToLong(ExecutionTime::getExecutionTime)
                .average()
                .orElse(0);
    }
}
