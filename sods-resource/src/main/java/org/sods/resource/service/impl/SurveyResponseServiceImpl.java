package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.SurveyResponse;
import org.sods.resource.mapper.ActiveSurveyMapper;
import org.sods.resource.mapper.SurveyResponseMapper;
import org.sods.resource.service.SurveyResponseService;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;
    @Autowired
    private ActiveSurveyMapper activeSurveyMapper;


    @Override
    public ResponseResult submitSurveyWithData(String payload,Long activeSurveyID) {
        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(activeSurveyID);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to create response: Active Survey is not find");
        }

        if(!target.getAllowAnonymous()){

            //Get user info
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Object principal = authentication.getPrincipal();

            //Get User ID => if (No login, userid:-1)
            if(!(principal instanceof LoginUser)){
                return new ResponseResult(403,"Failed to create response: Permission is not enough for these survey");
            }
        }

        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setResponseData(payload);
        surveyResponse.setActiveSurveyId(activeSurveyID);
        surveyResponseMapper.insert(surveyResponse);
        return new ResponseResult(200,"New survey response is created");
    }
}
