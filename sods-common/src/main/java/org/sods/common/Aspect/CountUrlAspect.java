package org.sods.common.Aspect;

import org.aspectj.lang.JoinPoint;

import org.aspectj.lang.annotation.AfterReturning;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
@Order(2)
public class CountUrlAspect {

    private static final Logger logger = LoggerFactory.getLogger(CountUrlAspect.class);
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
                logger.warn("Request to "+urlWithoutHost+" failed. Error Code:"+tmp.getCode().toString());
            }

            Object value = redisCache.getCacheObject(redisKey);
            if(Objects.isNull(value)){
                logger.info("Request to "+urlWithoutHost+" successed. Count:1");
                redisCache.setCacheObject(redisKey,1,30, TimeUnit.MINUTES);
            }else {
                int count = (int) value;
                count++;
                logger.info("Request to "+urlWithoutHost+" successed. Count:"+count);
                redisCache.setCacheObject(redisKey, count, 30, TimeUnit.MINUTES);
            }

        }


    }

}
