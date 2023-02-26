package org.sods.websocket.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.security.domain.LoginUser;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.RESTVotingService;
import org.sods.websocket.service.WebSocketRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RESTVotingServiceImpl implements RESTVotingService {
    @Autowired
    private WebSocketRedisService webSocketRedisService;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult createGroup(String rawPassCode,String surveyID) {
        Boolean success = webSocketRedisService.checkIfKeyExist(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));
        if(success){
            return new ResponseResult<>(400,"Create voting with code "+rawPassCode+" failed: The group exist.");
        }
        Long SurveyIDLong;

        try {
           SurveyIDLong = Long.parseLong(JSONObject.parseObject(surveyID).getString("surveyID"));
        }catch (Error error){
            return new ResponseResult<>(400,"Create voting with code "+rawPassCode+" failed: Wrong Format of the survey id.");
        }


        Survey survey = surveyMapper.selectById(SurveyIDLong);

        if(Objects.isNull(survey)){
            return new ResponseResult<>(400,"Create voting with code "+rawPassCode+" " +
                    "failed: The survey with id "+surveyID+" is not exist.");
        }

        VotingState votingState = new VotingState(rawPassCode,SurveyIDLong.toString(),survey.getSurveyFormat());
        webSocketRedisService.setObjectIfKeyNotExist(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode),
                votingState);

        return new ResponseResult<>(200,"Create voting with code "+rawPassCode+" success.");
    }

    @Override
    public ResponseResult removeGroup(String rawPassCode) {
        Boolean success = webSocketRedisService.checkIfKeyExist(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));
        if(!success){
            return new ResponseResult<>(400,"Delete voting with code "+rawPassCode+" failed: The group is not exist.");
        }

        Boolean success2 = webSocketRedisService.deleteVotingGroup(rawPassCode);

        if(!success2){
            return new ResponseResult<>(400,"Delete voting with code "+rawPassCode+" failed: Caching Problem.");
        }

        simpMessagingTemplate.convertAndSendToUser(rawPassCode, "/private",
                Message.getServerMessage(rawPassCode, Action.FORCEUNSUBSCRIBE, Status.COMMAND,
                        JsonDataResponse.getStringWithKey("msg","Group is removed:"
                                + rawPassCode)));



        return new ResponseResult<>(200,"Delete voting with code "+rawPassCode+" success.");
    }

    @Override
    public ResponseResult checkGroup(String rawPassCode) {
        Boolean success = webSocketRedisService.checkIfKeyExist(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));
        if(success){
            return new ResponseResult<>(200,"The voting with code "+rawPassCode+" is exist.");
        }else{
            return new ResponseResult<>(404,"The voting with code "+rawPassCode+" is not exist.");
        }

    }

    @Override
    public ResponseResult getExistGroups() {
        List<VotingState> votingStates = webSocketRedisService.getExistVotingGroup();
        List<Map> result = new ArrayList<>();
        votingStates.forEach((e)->{
            result.add(e.getJSONMapResponse());
        });
        return new ResponseResult<>(200,"Voting group return",result);
    }

    @Override
    public ResponseResult checkUserSubmit(String rawPassCode) {
        Long userid = getUserID();
        if(userid<0){
            return new ResponseResult<>(400,"User Request invalid");
        }
        //Get Global Data
        String globalVotingDataKey = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(globalVotingDataKey);
        if(Objects.isNull(votingState)){
            return new ResponseResult<>(400,"The voting with code "+rawPassCode+" is not exist.");
        }

        Integer currentQuestioon = votingState.getCurrentQuestion();

        //Get User Data
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userid.toString());
        UserVotingResponse userVotingResponse = redisCache.getCacheObject(userKey);
        Map<String,Boolean> result = new HashMap<>();


        if(Objects.isNull(userVotingResponse)){
            result.put("isSubmit",false);

        } else if (userVotingResponse.getUserData().containsKey(currentQuestioon.toString())) {
            result.put("isSubmit",true);
        } else{
            result.put("isSubmit",false);
        }



        return new ResponseResult<>(200,"Success",result);
    }

    @Override
    public ResponseResult submitResponse(String rawPassCode,String payload) {
        Long userid = getUserID();
        //Get Global Data
        String globalVotingDataKey = VotingState.getGlobalVotingDataRedisKeyString(rawPassCode);
        VotingState votingState = redisCache.getCacheObject(globalVotingDataKey);
        if(Objects.isNull(votingState)){
            return new ResponseResult<>(400,"User Request invalid");
        }

        String questionKey;
        //Check the user action
        if(JSONObject.parseObject(payload).containsKey("key")){
            questionKey = JSONObject.parseObject(payload).getString("key");
        }else{
            return new ResponseResult<>(400,"User Response format invalid");
        }




        //Not allow to submit at SHOWRESULT stage
        if(votingState.getClientRenderMethod().equals(ClientRenderMethod.SHOWRESULT)){

            return new ResponseResult<>(400,"User is not able to submit the data in this stage.");
        } else if(!votingState.getCurrentQuestion().toString().equals(questionKey)){

            return new ResponseResult<>(400,"User Response format invalid");
        }

        //Update User Data
        String userKey = VotingState.getUserResponseRedisKeyString(rawPassCode,userid.toString());
        UserVotingResponse userVotingResponse = redisCache.getCacheObject(userKey);
        Integer code = userVotingResponse.addDataToMap(payload);
        // 0 -> Success, 1 -> format error, 2 -> repeat
        if(code.equals(1)){
            return new ResponseResult<>(400,"User Response format invalid");
        } else if (code.equals(2)) {
            return new ResponseResult<>(400,"User Repeat Response");
        }

        redisCache.setCacheObject(userKey,userVotingResponse);

        //Update Global Data (Add submitted User List)
        votingState.addParticipantSubmitIfNotExist(userid.toString());
        redisCache.setCacheObject(globalVotingDataKey,votingState);

        //Synchronization
        simpMessagingTemplate.convertAndSendToUser(rawPassCode,"/private",
                Message.getSynchronizationMessage(rawPassCode, votingState.getJSONResponse()));


        return new ResponseResult<>(200,"Success");
    }

    public Long getUserID(){
        Long userid;
        //Get user info
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(Objects.isNull(authentication)){
            userid = -999L;
        }else{
            Object principal = authentication.getPrincipal();

            //Get User ID => if (No login, userid:-1)
            if(principal instanceof LoginUser){
                LoginUser loginUser = ((LoginUser)principal);
                userid = loginUser.getUser().getId();
            }else{
                userid = -1L;
            }
        }

        return userid;
    }
}
