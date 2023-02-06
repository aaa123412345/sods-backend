package org.sods.resource.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        Long userid;
        //Get user info
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        //Get User ID => if (No login, userid:-1)
        if(principal instanceof LoginUser){
            LoginUser loginUser = ((LoginUser)principal);
            userid = loginUser.getUser().getId();
        }else{
            userid = -1L;
        }



        this.setFieldValByName("updateUserId", userid, metaObject);
        this.setFieldValByName("createUserId", userid, metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Long userid;
        //Get user info
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        Object principal = authentication.getPrincipal();

        //Get User ID => if (No login, userid:-1)
        if(principal instanceof LoginUser){
            LoginUser loginUser = ((LoginUser)principal);
            userid = loginUser.getUser().getId();
        }else{
            userid = -1L;
        }



        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateUserId", userid, metaObject);
    }
}
