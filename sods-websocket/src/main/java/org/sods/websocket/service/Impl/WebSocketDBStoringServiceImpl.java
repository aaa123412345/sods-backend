package org.sods.websocket.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.SurveyResponse;
import org.sods.resource.mapper.ActiveSurveyMapper;
import org.sods.resource.mapper.SurveyResponseMapper;
import org.sods.websocket.domain.UserVotingResponse;
import org.sods.websocket.domain.VotingState;
import org.sods.websocket.service.WebSocketDBStoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WebSocketDBStoringServiceImpl implements WebSocketDBStoringService {

    @Autowired
    private ActiveSurveyMapper activeSurveyMapper;
    @Autowired
    private SurveyResponseMapper surveyResponseMapper;
    @Override
    public Boolean saveToDB(VotingState votingState, List<UserVotingResponse> userVotingResponseList) {

        Integer maxQ = votingState.getMaxQuestion();
        //Save VotingState in database and get the ID
        ActiveSurvey activeSurvey = activeSurveyConvertor(votingState);
        activeSurveyMapper.insert(activeSurvey);

        //Get the voting data in database back
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("information",activeSurvey.getInformation());
        activeSurvey = activeSurveyMapper.selectOne(queryWrapper);
        System.out.println(activeSurvey);
        Long activeSurveyID = activeSurvey.getActiveSurveyId();


        //Loop all userCacheKey
        userVotingResponseList.forEach((e)->{
            e.fillAllObjectIfKeyNotExist(maxQ);
            SurveyResponse surveyResponse = surveyResponseConvertor(e, votingState.getCurrentSurveyFormatPartKey(),
                    activeSurveyID);
            surveyResponseMapper.insert(surveyResponse);

        });


        return true;
    }

    @Override
    public ActiveSurvey activeSurveyConvertor(VotingState votingState) {
        ActiveSurvey activeSurvey = new ActiveSurvey();
        activeSurvey.setSurveyId(Long.parseLong(votingState.getSurveyID()));
        activeSurvey.setStartTime(votingState.getStartTime());
        activeSurvey.setEndTime(LocalDateTime.now());
        activeSurvey.setAllowAnonymous(false);
        activeSurvey.setAllowPublicSearch(false);
        activeSurvey.setPassCode(votingState.getPasscode());
        activeSurvey.setInformation("Voting of survey ("+votingState.getSurveyID()+
                "). From: "+activeSurvey.getStartTime()+" To: "+activeSurvey.getEndTime());
        return activeSurvey;
    }

    @Override
    public SurveyResponse surveyResponseConvertor(UserVotingResponse userVotingResponse,String partKey,Long activeSurveyID) {
        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setResponseData(userVotingResponse.toSurveyResponseJsonStingFormat(partKey));
        surveyResponse.setActiveSurveyId(activeSurveyID);

        return surveyResponse;
    }
}
