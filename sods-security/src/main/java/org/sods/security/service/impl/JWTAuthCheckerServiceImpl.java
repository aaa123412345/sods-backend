package org.sods.security.service.impl;

import io.jsonwebtoken.Claims;
import org.sods.common.utils.JwtUtil;
import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.sods.security.domain.User;
import org.sods.security.service.JWTAuthCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class JWTAuthCheckerServiceImpl implements JWTAuthCheckerService {
    @Autowired
    private RedisCache redisCache;
    @Override
    public LoginUser checkWithJWT(String token) {
        LoginUser anonymous = new LoginUser();
        User anonymousUser = new User();
        anonymousUser.setUserId(-1L);
        anonymous.setUser(anonymousUser);

        //Get token
        if(!StringUtils.hasText(token)){
            //allow to go through
            return anonymous;
        }
        //Analysis Token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        }catch (Exception e){
            return anonymous;
        }
        //Get user info from redis
        String redisKey = "login:"+userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            return anonymous;
        }
        return loginUser;
    }
}
