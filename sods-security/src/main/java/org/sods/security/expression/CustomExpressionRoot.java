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

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        List<String> permissions = loginUser.getPermissions();
        //If user is system root, user can use any service
        if(permissions.contains("system:root"))
            return true;

        //check related authority
        return permissions.contains(authority);
    }

    public Boolean isCurrentUser(String userID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(loginUser.getUser().getId() == Long.parseLong(userID)){
            return true;
        } else if (loginUser.getPermissions().contains("system:root")) {
            return true;
        }else{
            return false;
        }

    }
}
