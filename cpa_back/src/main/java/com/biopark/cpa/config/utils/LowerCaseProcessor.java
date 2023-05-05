package com.biopark.cpa.config.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LowerCaseProcessor {
    @Around("@annotation(com.biopark.cpa.config.utils.Lowercase)")
    public Object toLowerCase(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("asdsdasda");
        Object[] args = joinPoint.getArgs();
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                newArgs[i] = ((String) args[i]).toLowerCase();
            } else {
                newArgs[i] = args[i];
            }
        }
        return joinPoint.proceed(newArgs);
    }
}
