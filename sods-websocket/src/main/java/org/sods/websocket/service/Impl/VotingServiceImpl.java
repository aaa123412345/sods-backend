package org.sods.websocket.service.Impl;


import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
        //System.out.println("Join");
        String userName = webSocketSecurityService.getUserID(principal);
        votingState.addParticipantJoinIfNotExist(userName);
        redisCache.setCacheObject(passcode,votingState);

        //Add a redis cache for storing user response
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userName);
        webSocketRedisService.setObjectIfKeyNotExist(userKey,new UserVotingResponse(userName));



        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode,
                        votingState.getJSONResponseWithRenderData()));


        return message;
    }

    @Override
    public Message messageChannel(Message message,Principal principal) {

        //System.out.println("Message");
        switch (message.getAction()){

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

        //Check the channel if it is not exist
        if(Objects.isNull(votingState)){

            return null;
        }


        String userName = webSocketSecurityService.getUserID(principal);

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
            case NEXTQUESTION: return wsAdminActionService.setNextQuestion(message, principal);
            case SHOWRESULT: return wsAdminActionService.showResultOfCurrentQuestion(message, principal);
            case VOTINGEND: return wsAdminActionService.endVotingAndCollectData(message, principal);
        }
        return null;
    }

    @Override
    public Message adminjoinChannel(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        String passcode = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);

        VotingState votingState = redisCache.getCacheObject(passcode);
        //Check the channel if it is not exist

        if(Objects.isNull(votingState)){
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                    Message.getServerMessage(rawPassCode,Action.FORCEUNSUBSCRIBE,Status.COMMAND,null));
            return message;
        }

        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode,
                        votingState.getJSONResponseWithRenderData()));


        return message;
    }


}
