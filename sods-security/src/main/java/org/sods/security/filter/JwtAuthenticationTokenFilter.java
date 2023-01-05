package org.sods.security.filter;

import io.jsonwebtoken.Claims;
import org.sods.common.utils.JwtUtil;
import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Get token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            //allow to go through
            filterChain.doFilter(request,response);
            return;
        }
        //Analysis Token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Invalid token");
        }
        //Get user info from redis
        String redisKey = "login:"+userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("User do not login.");
        }
        //save to SecurityContextHolder
       //TODO Get the info of user permission
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //do futher filter
        filterChain.doFilter(request,response);
    }
}
