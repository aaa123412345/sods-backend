package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.sods.websocket.service.WebSocketSecurityService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class WebSocketSecurityServiceImpl implements WebSocketSecurityService {
    @Override
    public String getUserID(Principal principal) {
        JSONObject userObject = JSON.parseObject(principal.getName());
        return userObject.getString("UserID");
    }

    @Override
    public Boolean checkPermission(Principal principal, String requirement) {
        JSONObject userObject = JSON.parseObject(principal.getName());
        List<String> permission = (List<String>) userObject.get("Permission");
        if(permission.contains("System:root")){
            return true;
        }
        return permission.contains(requirement);
    }
}
