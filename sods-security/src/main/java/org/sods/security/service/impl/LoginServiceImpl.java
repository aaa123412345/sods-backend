package org.sods.security.service.impl;

import lombok.val;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.JwtUtil;
import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.sods.security.domain.User;
import org.sods.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseResult login(User user) {

        //User Validation
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //User info is wrong
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("Login Failed");
        }

        //User Info is correct
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        //save complete info of user to redis
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        redisCache.setCacheObject("login:"+userid,loginUser);

        return new ResponseResult(200,"login success",jwt);

    }

    @Override
    public ResponseResult logout() {
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        //remove the user info key in redis
        redisCache.deleteObject("login:"+id);
        return new ResponseResult(200,"Logout Success");
    }
}
