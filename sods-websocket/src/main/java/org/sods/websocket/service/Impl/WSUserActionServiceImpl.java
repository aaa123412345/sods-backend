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

    @Autowired
    private WebSocketSecurityService webSocketSecurityService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Message submitAction(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        String userName = webSocketSecurityService.getUserID(principal);

        //Get Global Data
        String globalVotingDataKey = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(globalVotingDataKey);

        //Check the user action
        String questionKey = JSONObject.parseObject(message.getData()).getString("key");



        //Not allow to submit at SHOWRESULT stage
        if(votingState.getClientRenderMethod().equals(ClientRenderMethod.SHOWRESULT)){

            return null;
        } else if(!votingState.getCurrentQuestion().toString().equals(questionKey)){

            return null;
        }

        //Update User Data
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userName);
        UserVotingResponse userVotingResponse = redisCache.getCacheObject(userKey);
        userVotingResponse.addDataToMap(message.getData());
        redisCache.setCacheObject(userKey,userVotingResponse);

        //Update Global Data (Add submitted User List)
        votingState.addParticipantSubmitIfNotExist(userName);
        redisCache.setCacheObject(globalVotingDataKey,votingState);

        //Synchronization
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));


        return null;
    }


}
