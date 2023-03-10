package org.sods.common.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
public class CountUrlAspect {

    @Autowired
    private RedisCache redisCache;


    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void apiRequests() {}

    @AfterReturning(pointcut = "apiRequests()", returning = "result")
    public void countApiRequests(JoinPoint joinPoint, Object result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();
        String urlWithoutHost = path.replace(request.getContextPath(), "");
        String redisKey = "COUNT:"+urlWithoutHost;

        if(result instanceof ResponseResult){
            ResponseResult tmp = (ResponseResult) result;
            if(tmp.getCode() >= 400){
                redisKey = "COUNT:"+tmp.getCode().toString();
            }

            Object value = redisCache.getCacheObject(redisKey);
            if(Objects.isNull(value)){
                redisCache.setCacheObject(redisKey,1,30, TimeUnit.MINUTES);
            }else {
                int count = (int) value;
                count++;
                redisCache.setCacheObject(redisKey, count, 30, TimeUnit.MINUTES);
            }

        }


    }

}
