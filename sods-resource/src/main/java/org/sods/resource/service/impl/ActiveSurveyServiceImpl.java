package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.BookingUserArriveData;
import org.sods.resource.domain.Survey;
import org.sods.resource.domain.SurveyResponse;
import org.sods.resource.mapper.ActiveSurveyMapper;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.resource.mapper.SurveyResponseMapper;
import org.sods.resource.service.ActiveSurveyService;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class ActiveSurveyServiceImpl implements ActiveSurveyService {

    @Autowired
    private ActiveSurveyMapper activeSurveyMapper;

    @Autowired
    private SurveyResponseMapper surveyResponseMapper;

    @Autowired
    private SurveyMapper surveyMapper;

    @Override
    public ResponseResult getDataWithActiveSurveyID(Long id) {
        ActiveSurvey target = activeSurveyMapper.selectById(id);
        if(Objects.isNull(target)){
            return new ResponseResult(HttpStatus.NOT_FOUND.value(), "Failed to get: Active Survey is not find");
        }

        return new ResponseResult(HttpStatus.OK.value(), "Active Survey Data: "+target.getSurveyId()+" is returned"
                , target);
    }

    @Override
    public ResponseResult getSurveyWithPassCode(String passcode) {
        //Search current ActiveSurvey with passcode
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("end_time",LocalDateTime.now());
        queryWrapper.le("start_time",LocalDateTime.now());
        queryWrapper.eq("pass_code",passcode);
        ActiveSurvey activeSurvey = activeSurveyMapper.selectOne(queryWrapper);
        if(Objects.isNull( activeSurvey)){
            return new ResponseResult(HttpStatus.NOT_FOUND.value(),"Failed to get: Passcode is not valid");
        }

        //If not Allow Anonymous, check the user
        if(!activeSurvey.getAllowAnonymous()){
            //Get user info
            SecurityContext securityContext = SecurityContextHolder.getContext();
            Authentication authentication = securityContext.getAuthentication();
            Object principal = authentication.getPrincipal();

            //Get User ID => if (No login, userid:-1)
            if(!(principal instanceof LoginUser)){
                return new ResponseResult(HttpStatus.FORBIDDEN.value(),"Failed to get: Permission is not enough for these survey");
            }
        }
        //Get Survey with the previous result id
        Survey target = surveyMapper.selectById(activeSurvey.getSurveyId());

        if(Objects.isNull(target)){
            return new ResponseResult(HttpStatus.NOT_FOUND.value(),"Failed to get: Survey is not find");
        }

        Map<String,Object> result = new HashMap<>();
        result.put("surveyID",target.getSurveyId().toString());
        result.put("surveyTitle",target.getSurveyTitle());
        result.put("surveyType",target.getSurveyType());
        result.put("surveyFormat",JSONObject.parseObject(target.getSurveyFormat()));
        result.put("activeSurveyID",activeSurvey.getActiveSurveyId().toString());
        result.put("information",activeSurvey.getInformation());



        return new ResponseResult(HttpStatus.OK.value(),"Result with Passcode: "+passcode+" is returned"
                ,result);

    }

    @Override
    public ResponseResult getDatasWithSurveyID(Long id) {
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("survey_id",id);
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(queryWrapper);
        return new ResponseResult(HttpStatus.OK.value(),"Get All Active Survey with Survey ID : "+id,surveyActiveList);
    }

    @Override
    public ResponseResult getAllActiveSurveyID() {
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(null);

        return new ResponseResult(HttpStatus.OK.value(),"Get All Active Survey",surveyActiveList);
    }

    @Override
    public ResponseResult getAllActiveSurveyIDWhichCurrentActive() {
        Long userid = getUserID();
        List<Long> userJoinedSurveyID = new ArrayList<>();

        //Get user submitted survey
        if(userid>0){
            QueryWrapper<SurveyResponse> responseQueryWrapper = new QueryWrapper<>();
            responseQueryWrapper.eq("create_user_id",userid);
            List<SurveyResponse> surveyResponseList = surveyResponseMapper.selectList(responseQueryWrapper);
            surveyResponseList.forEach((e)->{
                userJoinedSurveyID.add(e.getActiveSurveyId());
            });
        }

        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("end_time",LocalDateTime.now());
        queryWrapper.le("start_time",LocalDateTime.now());
        queryWrapper.eq("allow_public_search",true);

        //For Anonymous user, Only return the survey which allow anonymous response
        if(userid<0){
            queryWrapper.eq("allow_anonymous",true);
        }

        List<Map> tmpList =
                ActiveSurvey.getJsonResultforClient(activeSurveyMapper.selectList(queryWrapper));

        //Only return the survey that user do not write
        List<Map> resultList = new ArrayList<>();
        tmpList.forEach((e)->{
            if(!userJoinedSurveyID.contains(e.get("activeSurveyId"))){
                resultList.add(e);
            }
        });


        return new ResponseResult(HttpStatus.OK.value(),"Get All current Active Survey ",resultList);
    }

    @Override
    public ResponseResult deleteDataWithActiveSurveyID(Long id) {
        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(), "Failed to delete: Active Survey is not find");
        }

        //save to database
        activeSurveyMapper.deleteById(target.getSurveyId());

        return new ResponseResult(HttpStatus.OK.value(),"Active Survey is deleted");
    }

    @Override
    public ResponseResult deleteDatasWithSurveyID(Long id) {
        UpdateWrapper<ActiveSurvey> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("survey_id",id);
        activeSurveyMapper.delete(updateWrapper);
        return new ResponseResult(HttpStatus.OK.value(),"Active Survey with Survey ID: "+id+" is deleted");
    }

    @Override
    public ResponseResult updateDataWithActiveSurveyID(Long id, ActiveSurvey payload) {
        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(HttpStatus.BAD_REQUEST.value(),"Failed to update: Active Survey is not find");
        }

        if(!Objects.isNull(payload.getStartTime())){
            target.setStartTime(payload.getStartTime());
        }
        if(!Objects.isNull(payload.getEndTime())){
            target.setEndTime(payload.getEndTime());
        }
        if(!Objects.isNull(payload.getSurveyId())){
            target.setSurveyId(payload.getSurveyId());
        }


        //save to database
        activeSurveyMapper.updateById(target);
        return new ResponseResult(HttpStatus.OK.value(),"Active Survey is update");
    }



    @Override
    public ResponseResult createNewActiveSurvey(ActiveSurvey payload) {
        //Generate a random pass code
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        String passcode = "";
        do {
            passcode = RandomStringUtils.randomAlphabetic(6).toUpperCase();
            queryWrapper.eq("pass_code", passcode);
            queryWrapper.and(wrapper -> wrapper.ge("end_time",
                    payload.getStartTime()).or().le("start_time", payload.getEndTime()));
        }while (!Objects.isNull(activeSurveyMapper.selectOne(queryWrapper)));

        //Set the pass code for activated survey (Unique passcode in this survey period)
        payload.setPassCode(passcode);

        //save to database
        activeSurveyMapper.insert(payload);

        //Response to user
        return new ResponseResult(HttpStatus.OK.value(),"New Active survey is created");
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

