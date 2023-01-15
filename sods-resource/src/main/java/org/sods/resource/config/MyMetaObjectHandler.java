package org.sods.resource.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.sods.security.domain.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();


        if(Objects.isNull(authentication)){
            userid = -1L;
        }else{
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            userid = loginUser.getUser().getId();
        }

        this.setFieldValByName("updateUserId", -1L, metaObject);
        this.setFieldValByName("createUserId", -1L, metaObject);
        this.setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Long userid;
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(authentication)){
            userid = -1L;
        }else{
            LoginUser loginUser = (LoginUser) authentication.getPrincipal();
            userid = loginUser.getUser().getId();
        }



        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateUserId", -1L, metaObject);
    }
}
