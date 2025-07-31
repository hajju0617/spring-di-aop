package com.spring.ch3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAdvice {
    @Around("execution(* com.spring.ch3.aop.MyMath.add*(..))")  // 해당 패턴(pointcut)에 맞는 메서드에 @Around가 적용됨. (부가 기능이 적용될 메서드의 패턴.)
    public Object methodCallLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // Before Advice
        // 메서드가 실행된 시간을 알 수 있도록 시작시간 저장.
        long startTime = System.currentTimeMillis();
        System.out.println("<<[start] " + proceedingJoinPoint
                .getSignature()
                .getName() +
                Arrays.toString(proceedingJoinPoint.getArgs()));    // proceedingJoinPoint.getArgs()가 배열이므로 Arrays.toString() 사용.

        // target의 메서드가 proceedingJoinPoint로 넘어와서 호출되는 거임.
        Object result = proceedingJoinPoint.proceed();  // target(MyMath)의 메서드를 호출.

        // After Advice
        System.out.println("result = " + result);
        System.out.println("[end]>> " + (System.currentTimeMillis() - startTime) + "ms");
        return result;
    }
}
