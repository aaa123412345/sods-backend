package org.sods.websocket.service;

import org.sods.common.domain.ResponseResult;

public interface RESTVotingService {
    ResponseResult createGroup(String rawPassCode,String surveyID);
    ResponseResult removeGroup(String rawPassCode);
    ResponseResult checkGroup(String rawPassCode);

    ResponseResult getExistGroups();

    ResponseResult checkUserSubmit(String rawPassCode);

    ResponseResult submitResponse(String rawPassCode,String payload);
}
