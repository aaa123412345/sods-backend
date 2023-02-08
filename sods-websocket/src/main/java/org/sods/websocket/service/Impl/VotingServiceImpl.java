package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.Message;
import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.MessageActionService;
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
    private MessageActionService messageActionService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message joinChannel(Message message, Principal principal) {

        System.out.println("Join");
        String passcode = "Voting:" + message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(Objects.isNull(votingState)){
            votingState = new VotingState();
            List<String> participant = new ArrayList<>();
            participant.add(principal.getName());
            votingState.setParticipant(participant);
            votingState.setPasscode(message.getReceiverName());
            votingState.setClickCount(0);
            redisCache.setCacheObject(passcode,votingState);
            System.out.println("Voting:"+message.getReceiverName() +" is created");
        }else{

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
            case ADD: return messageActionService.addAction(message, principal);
            case MINUS: return messageActionService.minusAction(message, principal);
            case CLEAR:return messageActionService.clearAction(message, principal);
        }

        String passcode = "Voting:" + message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject(passcode);
        votingState.setClickCount(votingState.getClickCount()+1);
        redisCache.setCacheObject(passcode,votingState);
        message.setData(JSONObject.toJSONString(votingState));
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
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
        return null;
    }


}
