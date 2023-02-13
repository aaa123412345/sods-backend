package org.sods.websocket.service;

import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.SurveyResponse;
import org.sods.websocket.domain.UserVotingResponse;
import org.sods.websocket.domain.VotingState;

import java.util.List;

public interface WebSocketDBStoringService {
    Boolean saveToDB(VotingState votingState, List<UserVotingResponse> userVotingResponseList);
    ActiveSurvey activeSurveyConvertor(VotingState votingState);
    SurveyResponse surveyResponseConvertor(UserVotingResponse userVotingResponse,String partKey,Long activeSurveyID);
}
