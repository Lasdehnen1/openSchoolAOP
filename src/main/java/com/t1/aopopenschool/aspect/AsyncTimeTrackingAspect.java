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

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AsyncTimeTrackingAspect {

    private final TrackingService trackingService;

    @Pointcut("@annotation(com.t1.aopopenschool.annotation.TrackAsyncTime)")
    public void trackingAsyncPointCut() {

    }

    @Around("trackingAsyncPointCut()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                log.info("Asynchronous started in AsyncTimeTrackingAspect at {} on thread: {}", start, Thread.currentThread().getName());
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
        future.thenRun(() -> {
            long spent = System.currentTimeMillis() - start;
            log.info("Execution time of {}: {} ms on thread: {}", joinPoint.getSignature(), spent, Thread.currentThread().getName());
            trackingService.saveMethodExecutionTime(joinPoint.getSignature().toShortString(), spent);
        });
        return future;
    }

}
