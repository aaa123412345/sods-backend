package org.sods.common.Aspect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.domain.RedisUrlTimeFlag;
import org.sods.common.domain.RedisUrlTimeRecord;
import org.sods.common.utils.RedisCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(1)
public class TimeRecordAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeRecordAspect.class);
    @Autowired
    private RedisCache redisCache;



    @Pointcut("@annotation(org.sods.common.annotation.TimeRecord)")
    public void timeRecordMethods() {}

    @Before("timeRecordMethods()")
    public void timeRecord() {
        logger.info("Time Record Annotation is called.");
        String userID = getUserIDString();
        LocalDateTime currentTime = LocalDateTime.now();
        String url = getUrl();

        String flagKey = "TIMERECORD:FLAG:" + userID;
        logger.info("Flag Key: " + flagKey);
        Object flag = redisCache.getCacheObject(flagKey);

        if(Objects.isNull(flag)) {
            logger.info("First time to visit this url.");
            redisCache.setCacheObject(flagKey, new RedisUrlTimeFlag(url, currentTime), 20, TimeUnit.MINUTES);
        } else {
            RedisUrlTimeFlag tmp = (RedisUrlTimeFlag) flag;
            //refresh the flag if the url is same

            if(tmp.getUrl().equals(url)) {
                logger.info("Refresh the flag.");
                redisCache.setCacheObject(flagKey, tmp, 20, TimeUnit.MINUTES);
            } else {
                logger.info("The url is different.");
                //get the redisUrlTimeRecord and save it to redis if the url is different
                String newUID;
                if(userID.startsWith("Anonymous")) {
                    newUID = "Anonymous";
                } else {
                    newUID = userID.substring(userID.indexOf("@")+1);
                }
                RedisUrlTimeRecord redisUrlTimeRecord=
                RedisUrlTimeFlag.toRedisUrlTimeRecord(tmp, currentTime, newUID);

                redisCache.setCacheObject(flagKey, new RedisUrlTimeFlag(url, currentTime), 25, TimeUnit.MINUTES);
                //get uuid
                String uuid = UUID.randomUUID().toString();
                String recordKey = "TIMERECORD:RECORD:" + uuid;
                redisCache.setCacheObject(recordKey, redisUrlTimeRecord, 25, TimeUnit.MINUTES);

            }
        }


    }
    public String getUrl(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String path = request.getRequestURI();
        String urlWithoutHost = path.replace(request.getContextPath(), "");
        return urlWithoutHost;
    }

    public String getUserIDString() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            if(Objects.isNull(request.getHeader("deviceID"))) {
                return "Anonymous@"+request.getRemoteHost();
            }else {
                return "Anonymous@" + request.getHeader("deviceID");
            }
        } else {
            Object principal = authentication.getPrincipal();
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String tmpId = "-1";

            try {
                String json = mapper.writeValueAsString(principal);
                JsonNode jsonNode = mapper.readTree(json);

                tmpId = jsonNode.get("user").get("userId").asText();

            }catch (Exception e){
                e.printStackTrace();
            }


            // The user is authenticated
            return "User@" + tmpId;
        }
    }
}
