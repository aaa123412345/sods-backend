package org.sods.websocket.controller;

import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.Message;
import org.sods.websocket.domain.Status;
import org.sods.websocket.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller

public class VoteController {

    @Autowired
    private VotingService votingService;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){
    /*
       LoginUser u = jwtAuthCheckerService.checkWithJWT(message.getData());



        System.out.println(u.getUser().toString());
        System.out.println(message.toString());*/
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        switch (message.getStatus()){
            case JOIN: return votingService.join(message);
            case MESSAGE:return votingService.message(message);
            case LEAVE:return votingService.leave(message);
        }

        return message;



    }
}
