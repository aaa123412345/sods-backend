package org.sods.common.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.annotation.RedisCacheable;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

//write AOP for RedisCacheable annotation to make it work
@Aspect
@Component
@Order(10)
public class RedisCacheableAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheableAspect.class);
    @Autowired
    private RedisCache redisCache;

    @Pointcut("@annotation(org.sods.common.annotation.RedisCacheable)")
    public void redisCacheableMethods() {}

    @Around("redisCacheableMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //Get the key and expire of the annotation
        String key = method.getAnnotation(RedisCacheable.class).key();
        String expire = method.getAnnotation(RedisCacheable.class).expire();
        //System.out.println(key);
        //System.out.println(expire);

        //Get user request URL without host
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        String pathAndQuery = (queryString == null) ? request.getRequestURI() : request.getRequestURI() + "?" + queryString;
        String urlWithoutHost = pathAndQuery.replace(request.getContextPath(), "");
        String redisKey = "CACHE:"+key+":"+urlWithoutHost;

        Object value = redisCache.getCacheObject(redisKey);

        if(value != null){
            if(value instanceof ResponseResult) {
                ResponseResult tmp = (ResponseResult) value;

                return new ResponseResult<>(tmp.getCode(), tmp.getMsg(), tmp.getData());
            }else return value;
        }

        //If cache is not exist, keep going
        Object proceed = joinPoint.proceed();
        logger.info("Cache with key "+redisKey+" is not exist, set a new cache to redis with expire "+expire+" seconds");
        //Save the cache
        redisCache.setCacheObject(redisKey, proceed, Integer.parseInt(expire), TimeUnit.SECONDS);

        return proceed;
    }

}

