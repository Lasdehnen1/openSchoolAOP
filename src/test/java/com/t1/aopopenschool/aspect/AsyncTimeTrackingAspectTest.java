package com.t1.aopopenschool.aspect;

import com.t1.aopopenschool.service.TrackingService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;

public class AsyncTimeTrackingAspectTest {
    @Mock
    private TrackingService trackingService;

    @Mock
    private Executor asyncExecutor;

    @Mock
    private ProceedingJoinPoint joinPoint;

    private AsyncTimeTrackingAspect aspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aspect = new AsyncTimeTrackingAspect(trackingService, asyncExecutor);
    }

    @Test
    void trackAsyncTimeTest() throws Throwable {
        MethodSignature methodSignature = mock(MethodSignature.class);

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringTypeName()).thenReturn("com.t1.aopopenschool.service.ExampleService");
        when(methodSignature.getName()).thenReturn("computePrimeAsync");

        Object result = new Object();
        when(joinPoint.proceed()).thenReturn(result);

        aspect.trackAsyncTime(joinPoint);

        verify(asyncExecutor, times(1)).execute(any(Runnable.class));
        verify(trackingService, never()).saveMethodExecutionTime(anyString(), anyString(), anyLong());
    }
}
