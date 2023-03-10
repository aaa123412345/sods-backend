package org.sods.common.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RESTAspect {
    private static final Logger logger = LoggerFactory.getLogger(RESTAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putRequests() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void deleteRequests() {}
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void createRequests() {}

    @Around("putRequests()")
    public Object putMethodlog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Update Method {} is called with args {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        logger.info("Update Method {} return {}", joinPoint.getSignature().getName(), result);

        return result;
    }
    @Around("deleteRequests()")
    public Object deleteMethodlog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Delete Method {} is called with args {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        logger.info("Delete Method {} return {}", joinPoint.getSignature().getName(), result);

        return result;
    }
    @Around("createRequests()")
    public Object postMethodlog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Create Method {} is called with args {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        logger.info("Create Method {} return {}", joinPoint.getSignature().getName(), result);

        return result;
    }
}
