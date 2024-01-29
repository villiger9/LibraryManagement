package com.example.library.aspect;

import io.micrometer.core.annotation.Timed;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Before("@annotation(timed)")
    public void beforeMethod(Timed timed) {
        String methodName = timed.value();
        // Log or print the method name or any other metrics you want to measure
        System.out.println("Method " + methodName + " is being executed.");
    }
}
