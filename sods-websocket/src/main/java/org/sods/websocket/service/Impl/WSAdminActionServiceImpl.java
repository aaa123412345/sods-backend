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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WSAdminActionServiceImpl implements WSAdminActionService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private JWTAuthCheckerService jwtAuthCheckerService;

    @Autowired
    private WSUserActionService messageActionService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message CLEAR(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        System.out.println("Clear Group:" + rawPasscode);
        String passcode = "Voting:" + message.getReceiverName();

        VotingState votingState = new VotingState();
        votingState.setPasscode(rawPasscode);
        votingState.setParticipant(new ArrayList<>());
        votingState.setClickCount(0);
        redisCache.setCacheObject(passcode,votingState);
        message.setStatus(Status.COMMAND);
        message.setAction(Action.SYNCHRONIZATION);
        message.setData(JSONObject.toJSONString(votingState));
        simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private", message);
        return message;
    }

    @Override
    public Message RemoveGroup(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        System.out.println("Remove Group");
        String passcode = "Voting:" + message.getReceiverName();
        try {
            redisCache.deleteObject(passcode);
        }catch (Error e){
            System.out.println(e.toString());
        }
        message.setData("Group is removed:"+rawPasscode);
        message.setStatus(Status.COMMAND);
        message.setAction(Action.FORCEUNSUBSCRIBE);
        message.setData("Removed Group:"+ rawPasscode);
        simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private", message);


        return message;
    }

    @Override
    public Message CreateGroup(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        System.out.println("Create Group:" + rawPasscode);
        String passcode = "Voting:" + message.getReceiverName();

        VotingState votingState = new VotingState();
        votingState.setPasscode(rawPasscode);
        votingState.setParticipant(new ArrayList<>());
        votingState.setClickCount(0);
        redisCache.setCacheObject(passcode,votingState);
        message.setStatus(Status.MESSAGE);
        message.setAction(Action.NONE);
        message.setData("Created Group:"+ rawPasscode);
        simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private", message);
        return message;
    }

}
