<<<<<<< HEAD
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
=======
// LoggingAspect.java
package com.example.library.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
>>>>>>> e3423756a8e62ebe3ac874303a845b3039def7c5
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

<<<<<<< HEAD
    @Around("@annotation(com.example.library.aspect.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
=======
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("{} executed in {} ms", logExecutionTime.value(), executionTime);

        return result;
    }

    @Around("execution(* com.example.library.controller.LibraryController.addBook(..)) && @annotation(logExecutionTime)")
    public Object logAddBookExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        return logExecutionTime(joinPoint, logExecutionTime);
    }

    // Add similar loggers for other operations, if needed
}
>>>>>>> e3423756a8e62ebe3ac874303a845b3039def7c5
