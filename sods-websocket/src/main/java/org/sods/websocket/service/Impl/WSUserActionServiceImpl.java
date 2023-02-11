package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.WSUserActionService;
import org.sods.websocket.service.WebSocketSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
public class WSUserActionServiceImpl implements WSUserActionService {
    @Autowired
    private RedisCache redisCache;

    private WebSocketSecurityService webSocketSecurityService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Override
    public Message addAction(Message message, Principal principal) {

        String rawPassCode = message.getReceiverName();
        String passcode = "Voting:" + rawPassCode;
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(!Objects.isNull(votingState)) {
            //votingState.setClickCount(votingState.getClickCount() + 1);
            redisCache.setCacheObject(passcode, votingState);

            //Synchronization
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                    Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));

        }
        return message;
    }

    @Override
    public Message minusAction(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        String passcode = "Voting:" + rawPassCode;
        VotingState votingState = redisCache.getCacheObject(passcode);
        if(!Objects.isNull(votingState)) {
           // votingState.setClickCount(votingState.getClickCount() - 1);
            redisCache.setCacheObject(passcode, votingState);

            //Synchronization
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                    Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));
        }
        return message;
    }

    @Override
    public Message submitAction(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        String userName = webSocketSecurityService.getUserName(principal);

        //Update User Data
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userName);
        UserVotingResponse userVotingResponse = redisCache.getCacheObject(userKey);
        userVotingResponse.addDataToMap(message.getData());
        redisCache.setCacheObject(userKey,userVotingResponse);

        //Update Global Data (Add submitted User List)
        String globalVotingDataKey = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(globalVotingDataKey);
        votingState.addParticipantSubmitIfNotExist(userName);
        redisCache.setCacheObject(globalVotingDataKey,votingState);

        //Synchronization
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));


        return null;
    }


}
