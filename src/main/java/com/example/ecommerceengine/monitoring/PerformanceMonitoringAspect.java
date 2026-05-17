package com.example.ecommerceengine.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceMonitoringAspect {

    @Around("execution(* com.example.ecommerceengine..*(..)) && " +
            "(within(com.example.ecommerceengine.product..*) || " +
            "within(com.example.ecommerceengine.order..*) || " +
            "within(com.example.ecommerceengine.wallet..*) || " +
            "within(com.example.ecommerceengine.report..*))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("[AOP PERFORMANCE] " + className + "." + methodName +
                " executed in " + duration + " ms");

        return result;
    }
}