package com.t1.aopopenschool.aspect;

import com.t1.aopopenschool.service.TrackingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class TimeTrackingAspect {
    private final TrackingService trackingService;

    @Pointcut("@annotation(com.t1.aopopenschool.annotation.TrackTime)")
    public void trackingPointCut() {

    }

    @Around("trackingPointCut()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long spent = System.currentTimeMillis() - start;

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        log.info("Execution time of {}: {} ms", joinPoint.getSignature(), spent);
        trackingService.saveMethodExecutionTime(className, methodName, spent);
        return result;
    }

}

