package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.sods.websocket.service.WebSocketSecurityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketSecurityServiceImpl implements WebSocketSecurityService {
    @Override
    public String getUserName(String jsonString) {
        JSONObject userObject = JSON.parseObject(jsonString);
        return userObject.getString("UserName");
    }

    @Override
    public Boolean checkPermission(String jsonString, String requirement) {
        JSONObject userObject = JSON.parseObject(jsonString);
        List<String> permission = (List<String>) userObject.get("Permission");
        return permission.contains(requirement);
    }
}
