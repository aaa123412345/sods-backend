package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.Message;
import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class VotingServiceImpl implements VotingService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private JWTAuthCheckerService jwtAuthCheckerService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message join(Message message) {

        System.out.println("Join");
        String passcode = "Voting:" + message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(Objects.isNull(votingState)){
            votingState = new VotingState();
            votingState.setPasscode(message.getReceiverName());
            votingState.setParticipant(1);
            votingState.setClickCount(0);
            redisCache.setCacheObject(passcode,votingState);
            System.out.println("Voting:"+message.getReceiverName() +" is created");
        }else{
            votingState.setParticipant(votingState.getParticipant()+1);
            redisCache.setCacheObject(passcode,votingState);
        }

        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }

    @Override
    public Message message(Message message) {

        System.out.println("Message");
        String passcode = "Voting:" + message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject(passcode);
        votingState.setClickCount(votingState.getClickCount()+1);
        redisCache.setCacheObject(passcode,votingState);
        message.setData(JSONObject.toJSONString(votingState));
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }

    @Override
    public Message leave(Message message) {
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println("Leave");
        return message;
    }
}
