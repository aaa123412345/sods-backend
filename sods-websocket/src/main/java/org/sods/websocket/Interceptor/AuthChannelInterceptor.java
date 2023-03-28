package org.sods.websocket.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import org.sods.security.domain.LoginUser;
import org.sods.security.service.JWTAuthCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Component
public class AuthChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private JWTAuthCheckerService jwtAuthCheckerService;
/*
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        //Check if it is connected in the first time
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            //2、判断token
            List<String> nativeHeader = accessor.getNativeHeader("token");
            if (nativeHeader != null && !nativeHeader.isEmpty()) {
                String token = nativeHeader.get(0);
                if (StringUtils.isNotBlank(token)) {
                    //todo,通过token获取用户信息，下方用loginUser来代替
                    LoginUser loginUser = jwtAuthCheckerService.checkWithJWT(token);
                    if (loginUser != null) {
                        //如果存在用户信息，将用户名赋值，后期发送时，可以指定用户名即可发送到对应用户
                        Principal principal = new Principal() {
                            @Override
                            public String getName() {
                                return loginUser.getUsername();
                            }
                        };
                        accessor.setUser(principal);
                        return message;
                    }
                }
            }
            return null;
        }
        //不是首次连接，已经登陆成功
        return message;
    }
*/
@Override
public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    String token = getToken(message);

    if (token != null && accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
        Principal user = null;
        LoginUser loginUser = jwtAuthCheckerService.checkWithJWT(token);


        Map<String,Object> userData = new HashMap<>();
        userData.put("Permission",loginUser.getPermissions());
        userData.put("UserID",loginUser.getUser().getUserId().toString());

        if(Objects.isNull(loginUser.getUsername())){
            System.out.println("Connect Fail");

            return null;
        }
        System.out.println("Connect OK");
        user = () -> JSONObject.toJSONString(userData);
        accessor.setUser(user);


    }



    return message;
}

    private String getToken(Message<?> message){
        Map<String,Object> headers = (Map<String, Object>) message.getHeaders().get("nativeHeaders");
        if (headers !=null && headers.containsKey("token")){
            List<String> token = (List<String>)headers.get("token");
            return String.valueOf(token.get(0));
        }
        return null;
    }
}
