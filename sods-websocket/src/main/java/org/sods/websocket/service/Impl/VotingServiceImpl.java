package org.sods.websocket.service.Impl;


import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
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
    private WebSocketSecurityService webSocketSecurityService;
    @Autowired
    private WebSocketRedisService webSocketRedisService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message joinChannel(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        String passcode = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);

        VotingState votingState = redisCache.getCacheObject(passcode);
        //Check the channel if it is not exist

        if(Objects.isNull(votingState)){
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                    Message.getServerMessage(rawPassCode,Action.FORCEUNSUBSCRIBE,Status.COMMAND,null));
            return message;
        }
        //Add people list in voting state (global)
        System.out.println("Join");
        String userName = webSocketSecurityService.getUserName(principal);
        votingState.addParticipantJoinIfNotExist(userName);
        redisCache.setCacheObject(passcode,votingState);

        //Add a redis cache for storing user response
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userName);
        webSocketRedisService.setObjectIfKeyNotExist(userKey,new UserVotingResponse(userName));



        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));


        return message;
    }

    @Override
    public Message messageChannel(Message message,Principal principal) {

        System.out.println("Message");
        switch (message.getAction()){
            case ADD: return wsUserActionService.addAction(message, principal);
            case MINUS: return wsUserActionService.minusAction(message, principal);
            case SUBMIT:return wsUserActionService.submitAction(message, principal);

        }


        return message;
    }

    @Override
    public Message leaveChannel(Message message,Principal principal) {

        //Get data
        System.out.println("Leave");
        String rawPassCode = message.getReceiverName();
        String passcode = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(passcode);
        String userName = webSocketSecurityService.getUserName(principal);

        //Remove user if exist
        votingState.removeParticipantJoinIfExist(userName);
        redisCache.setCacheObject(passcode,votingState);

        //SEND SYNCHRONIZATION MESSAGE
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));
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
