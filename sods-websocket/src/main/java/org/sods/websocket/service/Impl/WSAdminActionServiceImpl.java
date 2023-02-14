package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.utils.RedisCache;
import org.sods.security.service.JWTAuthCheckerService;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.WSAdminActionService;
import org.sods.websocket.service.WSUserActionService;
import org.sods.websocket.service.WebSocketDBStoringService;
import org.sods.websocket.service.WebSocketRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.parameters.P;
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
    private WebSocketDBStoringService webSocketDBStoringService;

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


        //Try to Remove
        Boolean success = webSocketRedisService.deleteVotingGroup(rawPasscode);
        if(success){
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                            JsonDataResponse.getStringWithKey("msg","Remove Group Success:" + rawPasscode)));
        }else{
            simpMessagingTemplate.convertAndSendToUser(rawPasscode, "/private",
                    Message.getServerMessage(rawPasscode,Action.NONE,Status.MESSAGE,
                            JsonDataResponse.getStringWithKey("msg","Remove Group Failed (Group is not exist):"
                                    + rawPasscode)));
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

        VotingState votingState = new VotingState(rawPasscode,surveyID,surveyFormat);


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
        //Get voting State
        String rawPassCode = message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject
                (VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));

        //Collect All User Response In Cache (Current question) <- Missing question data replace with null (All user)
        List<String> userCacheKey = votingState.getUserResponseRedisKeyList();
        QuestionDataGrouper questionDataGrouper = new QuestionDataGrouper(votingState.getCurrentQuestionType());

        //Loop all userCacheKey
        String questionKey = votingState.getCurrentQuestion().toString();
        userCacheKey.forEach((e)->{
            UserVotingResponse u = redisCache.getCacheObject(e);
            //CurrentQuestion (int) is a key of user respond => Check key if it is exist
            Boolean keyExist = u.fillObjectIfKeyNotExist(questionKey);
            if(!keyExist){
                redisCache.setCacheObject(e,u);
            }else{
                //If key exist, Do the data grouping
                questionDataGrouper.collectDataAndPutIntoMap(u.getUserData().get(questionKey));
            }
        });

        //Select the best client-side rendering method
        questionDataGrouper.processDataBeforeShare();

        //Update the Voting State
        votingState.setClientRenderMethod(ClientRenderMethod.SHOWRESULT);
        votingState.setRenderData(JSONObject.toJSONString(questionDataGrouper));
        redisCache.setCacheObject(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode),votingState);

        //Return the grouping result with user
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode,
                        votingState.getJSONResponseWithRenderData()));

        return message;
    }

    @Override
    public Message setNextQuestion(Message message, Principal principal) {
        String rawPassCode = message.getReceiverName();

        //Get Global Data
        String globalVotingDataKey = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(globalVotingDataKey);

        //Set the voting state to next question
        Boolean success = votingState.checkAndSetToNextQuestion();

        if(success){
            //Update the Voting State
            redisCache.setCacheObject(globalVotingDataKey,votingState);
            //Syn the new question for all user in this voting
            simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                    Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponseWithRenderData()));

        }else {
            return null;
        }

        return message;
    }

    @Override
    public Message endVotingAndCollectData(Message message, Principal principal) {
        //Get global voting State
        String rawPassCode = message.getReceiverName();
        VotingState votingState = redisCache.getCacheObject
                (VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));

        //Collect All User Response In Cache (Current question) <- Missing question data replace with null (All user)
        List<String> userCacheKey = votingState.getUserResponseRedisKeyList();
        List<UserVotingResponse> userVotingResponseList = new ArrayList<>();
        //Get the user response in Redis
        userCacheKey.forEach((e)->{
            userVotingResponseList.add(redisCache.getCacheObject(e));
        });

        //create the response record in database
        if(webSocketDBStoringService.saveToDB(votingState,userVotingResponseList)){
            //Clear all data of these voting in Redis Cache
            Boolean success = webSocketRedisService.deleteVotingGroup(rawPassCode);
        }


        //Send Message to user to let them know the voting is ended
        simpMessagingTemplate.convertAndSendToUser(rawPassCode, "/private",
                Message.getServerMessage(rawPassCode,Action.NONE,Status.MESSAGE,
                        JsonDataResponse.getStringWithKey("msg","Voting End" + rawPassCode)));

        //Force unsubscribe
        simpMessagingTemplate.convertAndSendToUser(rawPassCode, "/private",
                Message.getServerMessage(rawPassCode,Action.FORCEUNSUBSCRIBE,Status.COMMAND, null));
        return message;
    }

}
