package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;

public interface ActiveSurveyService {
    ResponseResult getDataWithActiveSurveyID(Long id);
    ResponseResult getDatasWithSurveyID(Long id);
    ResponseResult getAllActiveSurveyID();
    ResponseResult deleteDataWithActiveSurveyID(Long id);
    ResponseResult deleteDatasWithSurveyID(Long id);
    ResponseResult updateDataWithActiveSurveyID(Long id, ActiveSurvey payload);

    ResponseResult createNewActiveSurvey(ActiveSurvey payload);
}
