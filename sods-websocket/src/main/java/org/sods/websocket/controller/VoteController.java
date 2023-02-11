package org.sods.websocket.controller;

import org.sods.websocket.domain.Message;
import org.sods.websocket.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;


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
    public Message recMessage(@Payload Message message, Principal principal){
        message.setSenderName(principal.getName());

        //Websocket require user to login

            switch (message.getStatus()) {
                case JOIN:
                    return votingService.joinChannel(message, principal);
                case MESSAGE:
                    return votingService.messageChannel(message, principal);
                case LEAVE:
                    return votingService.leaveChannel(message, principal);
                case COMMAND:
                    return votingService.commandForChannel(message,principal);

            }


        return null;



    }
}
