package com.t1.aopopenschool.service;

import com.t1.aopopenschool.model.ExecutionTime;
import com.t1.aopopenschool.repository.ExecutionTimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackingServiceTest {

    @Mock
    private ExecutionTimeRepository repository;

    private TrackingService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new TrackingService(repository);
    }

    @Test
    void saveMethodExecutionTimeTest() {
        service.saveMethodExecutionTime("com.t1.aopopenschool.service.ExampleService", "computePrimesSync", 100L);

        verify(repository, times(1)).save(any(ExecutionTime.class));
    }

    @Test
    void getExecutionTimesTest() {
        ExecutionTime time = new ExecutionTime();
        time.setMethodName("computePrimesSync");
        List<ExecutionTime> times = List.of(time);

        when(repository.findByMethodNameIgnoreCase("computePrimesSync")).thenReturn(times);

        List<ExecutionTime> result = service.getExecutionTimes("computePrimesSync");

        assertEquals(1, result.size());
        assertEquals("computePrimesSync", result.get(0).getMethodName());
    }

    @Test
    void getAverageExecutionTimeTest() {
        long number1 = 100L;
        long number2 = 200L;
        long number3 = 300L;

        ExecutionTime time1 = new ExecutionTime();
        time1.setExecutionTime(number1);
        ExecutionTime time2 = new ExecutionTime();
        time2.setExecutionTime(number2);
        ExecutionTime time3 = new ExecutionTime();
        time3.setExecutionTime(number3);

        List<ExecutionTime> times = List.of(time1, time2, time3);

        when(repository.findByMethodNameIgnoreCase("computePrimesSync")).thenReturn(times);

        double expectedAverage = (number1 + number2 + number3)/3;
        double actualAverage = service.getAverageExecutionTime("computePrimesSync");

        assertEquals(expectedAverage, actualAverage);
    }
}
