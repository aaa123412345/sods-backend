package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.ActiveSurveyMapper;
import org.sods.resource.service.ActiveSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ActiveSurveyServiceImpl implements ActiveSurveyService {

    @Autowired
    private ActiveSurveyMapper activeSurveyMapper;

    @Override
    public ResponseResult getDataWithActiveSurveyID(Long id) {
        ActiveSurvey target = activeSurveyMapper.selectById(id);
        if(Objects.isNull(target)){
            return new ResponseResult(404,"Failed to get: Active Survey is not find");
        }

        return new ResponseResult(200,"Active Survey Data: "+target.getSurveyId()+" is returned"
                , target);
    }

    @Override
    public ResponseResult getDatasWithSurveyID(Long id) {
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("survey_id",id);
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(queryWrapper);
        return new ResponseResult(200,"Get All Active Survey with Survey ID : "+id,surveyActiveList);
    }

    @Override
    public ResponseResult getAllActiveSurveyID() {
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(null);

        return new ResponseResult(200,"Get All Active Survey",surveyActiveList);
    }

    @Override
    public ResponseResult getAllActiveSurveyIDWhichCurrentActive() {
        QueryWrapper<ActiveSurvey> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("end_time",LocalDateTime.now());
        queryWrapper.le("start_time",LocalDateTime.now());
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(queryWrapper);
        return new ResponseResult(200,"Get All current Active Survey ",surveyActiveList);
    }

    @Override
    public ResponseResult deleteDataWithActiveSurveyID(Long id) {
        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to delete: Active Survey is not find");
        }

        //save to database
        activeSurveyMapper.deleteById(target.getSurveyId());

        return new ResponseResult(200,"Active Survey is deleted");
    }

    @Override
    public ResponseResult deleteDatasWithSurveyID(Long id) {
        UpdateWrapper<ActiveSurvey> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("survey_id",id);
        activeSurveyMapper.delete(updateWrapper);
        return new ResponseResult(200,"Active Survey with Survey ID: "+id+" is deleted");
    }

    @Override
    public ResponseResult updateDataWithActiveSurveyID(Long id, ActiveSurvey payload) {
        //get target survey object
        ActiveSurvey target = activeSurveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to update: Active Survey is not find");
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
        return new ResponseResult(200,"Active Survey is update");
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
        return new ResponseResult(200,"New Active survey is created");
    }
}

