package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        Long userId =getUserID();

        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(activeSurveyID);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to create response: Active Survey is not find");
        }

        if(!target.getAllowAnonymous() && userId<0) {
            return new ResponseResult(403, "Failed to create response: Permission is not enough for these survey");
        }

        if(userId>0){
            //Check if user submit exist
            QueryWrapper<SurveyResponse> surveyResponseQueryWrapper = new QueryWrapper<>();
            surveyResponseQueryWrapper.eq("active_survey_id",activeSurveyID);
            surveyResponseQueryWrapper.eq("create_user_id",userId);
            if(Objects.nonNull(surveyResponseMapper.selectOne(surveyResponseQueryWrapper))){
                return new ResponseResult(400,"Failed to create response: The response exist");
            }
        }



        SurveyResponse surveyResponse = new SurveyResponse();
        surveyResponse.setResponseData(payload);
        surveyResponse.setActiveSurveyId(activeSurveyID);
        surveyResponse.setUserId(userId);
        surveyResponseMapper.insert(surveyResponse);
        return new ResponseResult(200,"New survey response is created");
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
