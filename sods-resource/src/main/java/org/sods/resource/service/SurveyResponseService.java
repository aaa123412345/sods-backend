package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;



public interface SurveyResponseService {



    ResponseResult submitSurveyWithData(String payload,Long activeSurveyID);
}
