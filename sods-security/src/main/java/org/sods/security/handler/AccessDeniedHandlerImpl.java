package org.sods.security.handler;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sods.common.Aspect.CountUrlAspect;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(),"Permission denied");
        String json = JSON.toJSONString(result);
        logger.warn("Permission denied. Request to "+request.getRequestURI()+
                " failed. Error Code:"+result.getCode().toString()
                +" Device ID:"+ request.getHeader("deviceID") + "Device IP:" + request.getRemoteAddr());
        //处理异常
        WebUtils.renderString(response,json);
    }
}
