package org.sods.websocket.service.Impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.websocket.domain.*;
import org.sods.websocket.service.RESTVotingService;
import org.sods.websocket.service.WebSocketRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Objects;


public class RESTVotingServiceImpl implements RESTVotingService {
    @Autowired
    private WebSocketRedisService webSocketRedisService;

    @Autowired
    private SurveyMapper surveyMapper;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public ResponseResult createGroup(String rawPassCode,String surveyID) {
        Boolean success = webSocketRedisService.checkIfKeyExist(VotingState.getGlobalVotingDataRedisKeyString(rawPassCode));
        if(success){
            return new ResponseResult<>(400,"Create voting with code "+rawPassCode+" failed: The group exist.");
        }

        Survey survey = surveyMapper.selectById(Long.parseLong(surveyID));

        if(Objects.isNull(survey)){
            return new ResponseResult<>(400,"Create voting with code "+rawPassCode+" " +
                    "failed: The survey with id "+surveyID+" is not exist.");
        }

        VotingState votingState = new VotingState(rawPassCode,surveyID,survey.getSurveyFormat());
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
}
