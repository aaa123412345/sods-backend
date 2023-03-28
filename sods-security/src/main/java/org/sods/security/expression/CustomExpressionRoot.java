package org.sods.security.expression;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.Aspect.CountUrlAspect;
import org.sods.security.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


@Component("ex")
public class CustomExpressionRoot {
    private static final Logger logger = LoggerFactory.getLogger(CustomExpressionRoot.class);

    public boolean hasAuthority(String authority){

        //get the user login data
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        LoginUser loginUser;
        if(principal instanceof LoginUser){
            loginUser = ((LoginUser)principal);
        }else {
            logger.warn("User try to access the service without login.");
            return false;
        }


        List<String> permissions = loginUser.getPermissions();
        //If user is system root, user can use any service
        if(permissions.contains("system:root"))
            return true;


        //check related authority
        if(permissions.contains(authority)){
            return true;
        }else {
            logger.warn("User try to access the service without permission. " +
                    "User ID: "+loginUser.getUser().getUserId()+
                    ", Required Authority: "+authority);
            return false;
        }
    }

    public Boolean isCurrentUser(String userID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(loginUser.getUser().getUserId() == Long.parseLong(userID)){
            return true;
        } else if (loginUser.getPermissions().contains("system:root")) {
            return true;
        }else{
            return false;
        }

    }
}
