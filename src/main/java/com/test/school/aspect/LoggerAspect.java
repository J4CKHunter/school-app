package com.test.school.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* com.test.school..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{

        log.info(joinPoint.getSignature().toString() + ": method execution started.");
        Instant start = Instant.now();

        // execute business logic (call the actual method yani)
        Object returnObject = joinPoint.proceed();

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        log.info("Method: " + joinPoint.getSignature().toString() + " ---- Time took to execute : " + timeElapsed);
        log.info(joinPoint.getSignature().toString() + ": method execution ended.");

        return returnObject;
    }

}
