package com.t1.aopopenschool.aspect;

import com.t1.aopopenschool.service.TrackingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TimeTrackingAspectTest {
    @Mock
    private TrackingService trackingService;

    @Mock
    private ProceedingJoinPoint joinPoint;

    private TimeTrackingAspect aspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aspect = new TimeTrackingAspect(trackingService);
    }

    @Test
    void trackTimeTest() throws Throwable {
        MethodSignature methodSignature = mock(MethodSignature.class);
        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringTypeName()).thenReturn("com.t1.aopopenschool.service.ExampleService");
        when(methodSignature.getName()).thenReturn("computePrimesSync");

        Object result = new Object();
        when(joinPoint.proceed()).thenReturn(result);

        aspect.trackTime(joinPoint);

        verify(trackingService, times(1)).saveMethodExecutionTime(anyString(), anyString(), anyLong());
    }

}
