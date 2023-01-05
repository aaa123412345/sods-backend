package org.sods.security.expression;

import org.sods.security.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("ex")
public class CustomExpressionRoot {
    public boolean hasAuthority(String authority){

        //get the user login data
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //If user is anonymous but the request require any permission, return false
        if(authentication.getPrincipal().equals("anonymousUser")){
            return false;
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        List<String> permissions = loginUser.getPermissions();


        //check related authority
        return permissions.contains(authority);
    }
}
