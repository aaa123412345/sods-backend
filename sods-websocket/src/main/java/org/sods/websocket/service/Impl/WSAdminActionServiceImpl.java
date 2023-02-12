package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.WSAdminActionService;
import org.sods.websocket.service.WSUserActionService;
import org.sods.websocket.service.WebSocketRedisService;
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
    private WebSocketRedisService webSocketRedisService;

    @Autowired
    private RedisCache redisCache;
    @Override
    public Message CLEAR(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();
        System.out.println("Clear Group:" + rawPassCode);
        String passcode = "Voting:" + message.getReceiverName();

        //init the voting
        VotingState votingState = redisCache.getCacheObject(passcode);

        //Save to redis
        redisCache.setCacheObject(passcode,votingState);

        //Synchronization
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));
        return message;
    }

    @Override
    public Message RemoveGroup(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        System.out.println("Remove Group");
        String passcode = "Voting:" + message.getReceiverName();

        //Try to Remove
        Boolean success = webSocketRedisService.deleteObjectIfKeyExist(passcode);
        if(success){
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                            JsonDataResponse.getStringWithKey("msg","Create Group Success:" + rawPasscode)));
        }else{
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                            JsonDataResponse.getStringWithKey("msg","Create Group Failed (Group is exist):" + rawPasscode)));
        }


        return message;
    }

    @Override
    public Message CreateGroup(Message message, Principal principal) {
        String rawPasscode = message.getReceiverName();
        String surveyID = JSONObject.parseObject(message.getData()).getString("surveyID");
        String surveyFormat = JSONObject.toJSONString(JSONObject.parseObject(message.getData()).get("surveyFormat"));
        System.out.println("Create Group:" + rawPasscode);
        String passcode1 = VotingState.getGlobalVotingDataRedisKeyString(rawPasscode);

        VotingState votingState = new VotingState(rawPasscode);
        votingState.setSurveyID(surveyID);
        votingState.setSurveyFormat(surveyFormat);

        //Try to get
        Boolean success1 = webSocketRedisService.setObjectIfKeyNotExist(passcode1,votingState);

        if(success1){
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                    JsonDataResponse.getStringWithKey("msg","Create Group Success:" + rawPasscode)));
        }else{
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                    JsonDataResponse.getStringWithKey("msg","Create Group Failed (Group is exist):" + rawPasscode)));
        }

        return message;
    }

    @Override
    public Message showResultOfCurrentQuestion(Message message, Principal principal) {
        //Collect All User Response In Cache (Current question) <- Missing question data replace with null (All user)
        //Do the data grouping
        //Update the Voting State
        //Return the grouping result with user
        return null;
    }

    @Override
    public Message setNextQuestion(Message message, Principal principal) {
        //Clear analysis data in previous votingState
        //Set the voting state to next question
        //Update the Voting State
        //Syn the new question for all user in this voting
        return null;
    }

    @Override
    public Message endVotingAndCollectData(Message message, Principal principal) {
        //Get the user response in Redis
        //create the response record in database
        //Clear all data of these voting in Redis Cache
        //Send Message to user to let them know the voting is ended
        return null;
    }

}
