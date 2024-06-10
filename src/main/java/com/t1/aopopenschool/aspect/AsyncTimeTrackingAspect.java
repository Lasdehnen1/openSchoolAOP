package com.t1.aopopenschool.aspect;

import com.t1.aopopenschool.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncTimeTrackingAspect {

    private final TrackingService trackingService;
    private final Executor asyncExecutor;

    @Pointcut("@annotation(com.t1.aopopenschool.annotation.TrackAsyncTime)")
    public void trackingAsyncPointCut() {

    }

    @Around("trackingAsyncPointCut()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long spent = System.currentTimeMillis() - start;

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        CompletableFuture.runAsync(() -> {
            log.info("Execution time of {}: {} ms on thread: {}", methodName, spent, Thread.currentThread().getName());
            trackingService.saveMethodExecutionTime(className, methodName, spent);
        }, asyncExecutor);
        return result;
    }

}
