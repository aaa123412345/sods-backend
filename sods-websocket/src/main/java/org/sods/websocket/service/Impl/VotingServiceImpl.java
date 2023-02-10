package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.Action;
import org.sods.websocket.domain.Message;
import org.sods.websocket.domain.Status;
import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.WSAdminActionService;
import org.sods.websocket.service.WSUserActionService;
import org.sods.websocket.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VotingServiceImpl implements VotingService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private JWTAuthCheckerService jwtAuthCheckerService;

    @Autowired
    private WSUserActionService wsUserActionService;

    @Autowired
    private WSAdminActionService wsAdminActionService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message joinChannel(Message message, Principal principal) {


        String rawPassCode = message.getReceiverName();
        String passcode = "Voting:" + rawPassCode;
        VotingState votingState = redisCache.getCacheObject(passcode);
        System.out.println(votingState);
        if(Objects.isNull(votingState)){
            message.setStatus(Status.COMMAND);
            message.setAction(Action.FORCEUNSUBSCRIBE);
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",message);
            return message;

        }else{
            System.out.println("Join");
            List<String> participant = votingState.getParticipant();
            participant.add(principal.getName());
            redisCache.setCacheObject(passcode,votingState);
        }

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }

    @Override
    public Message messageChannel(Message message,Principal principal) {

        System.out.println("Message");
        switch (message.getAction()){
            case ADD: return wsUserActionService.addAction(message, principal);
            case MINUS: return wsUserActionService.minusAction(message, principal);

        }


        return message;
    }

    @Override
    public Message leaveChannel(Message message,Principal principal) {

        System.out.println("Leave");
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

    @Override
    public Message commandForChannel(Message message, Principal principal) {
        System.out.println("Command");
        switch (message.getAction()){
            case CREATEGROUP: return  wsAdminActionService.CreateGroup(message, principal);
            case REMOVEGROUP: return wsAdminActionService.RemoveGroup(message, principal);
            case CLEAR:return wsAdminActionService.CLEAR(message, principal);
        }
        return null;
    }


}
