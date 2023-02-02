package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.ActiveSurveyMapper;
import org.sods.resource.service.ActiveSurveyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

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
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(null);
        return new ResponseResult(200,"Get All Active Survey with Survey ID : "+id,surveyActiveList);
    }

    @Override
    public ResponseResult getAllActiveSurveyID() {
        List<ActiveSurvey> surveyActiveList = activeSurveyMapper.selectList(null);

        return new ResponseResult(200,"Get All Active Survey",surveyActiveList);
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
        //save to database
        activeSurveyMapper.insert(payload);

        //Response to user
        return new ResponseResult(200,"New Active survey is created");
    }
}

