package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.resource.service.SurveyService;
import org.sods.security.domain.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.Objects;

@Service
public class SurveyServiceImpl implements SurveyService {
    @Autowired
    private SurveyMapper surveyMapper;
    @Override
    public ResponseResult get(Long id) {
        //Set query wrapper
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        surveyQueryWrapper.eq("delflag",0);
        surveyQueryWrapper.eq("survey_id",id);

        Survey target = surveyMapper.selectOne(surveyQueryWrapper);
        if(Objects.isNull(target)){
            return new ResponseResult(404,"Failed to get: Survey is not find");
        }

        return new ResponseResult(200,"Survey: "+target.getSurveyId()+" is returned",target);
    }

    @Override
    public ResponseResult listAll() {
        //Set query wrapper
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        surveyQueryWrapper.eq("delflag",0);
        List<Survey> surveyList = surveyMapper.selectList(surveyQueryWrapper);
        for (int i = 0; i < surveyList.size(); i++) {
            surveyList.get(i).setSurveyFormat("");
        }

        return new ResponseResult(200,"Get All",surveyList);
    }

    @Override
    public ResponseResult delete(Long id) {
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //Set query wrapper
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        surveyQueryWrapper.eq("delflag",0);
        surveyQueryWrapper.eq("survey_id",id);
        //get old survey object
        Survey old = surveyMapper.selectOne(surveyQueryWrapper);

        //Check if survey is not exist
        if(Objects.isNull(old)){
            return new ResponseResult(404,"Failed to delete: Survey is not find");
        }


        //update survey object
        LocalDateTime current = LocalDateTime.now();

        old.setUpdateUserId(userid);
        old.setUpdateTime(current);
        old.setDelflag(1);

        //update wrapper
        UpdateWrapper<Survey> surveyUpdateWrapper = new UpdateWrapper<>();
        surveyUpdateWrapper.eq("delflag",0);
        surveyUpdateWrapper.eq("survey_id",id);

        //save to database
        surveyMapper.update(old,surveyUpdateWrapper);



        return new ResponseResult(200,"Survey is deleted");
    }

    @Override
    public ResponseResult put(Long id, String payload) {
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        //Set query wrapper
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        surveyQueryWrapper.eq("delflag",0);
        surveyQueryWrapper.eq("survey_id",id);
        //get old survey object
        Survey old = surveyMapper.selectOne(surveyQueryWrapper);

        //Check if survey is not exist
        if(Objects.isNull(old)){
            return new ResponseResult(404,"Failed to update: Survey is not find");
        }

        //update survey object
        LocalDateTime current = LocalDateTime.now();

        old.setSurveyFormat(payload);
        old.setUpdateUserId(userid);
        old.setUpdateTime(current);

        //update wrapper
        UpdateWrapper<Survey> surveyUpdateWrapper = new UpdateWrapper<>();
        surveyUpdateWrapper.eq("delflag",0);
        surveyUpdateWrapper.eq("survey_id",id);

        //save to database
        surveyMapper.update(old,surveyUpdateWrapper);

        return new ResponseResult(200,"Survey is update");
    }

    @Override
    public ResponseResult post(String payload) {
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        //set survey object
        LocalDateTime current = LocalDateTime.now();
        Survey s = new Survey();
        s.setSurveyFormat(payload);
        s.setCreateUserId(userid);
        s.setUpdateUserId(userid);
        s.setCreateTime(current);
        s.setUpdateTime(current);

        //save to database
        surveyMapper.insert(s);

        //Response to user
        return new ResponseResult(200,"New survey is created");
    }

    public boolean formatChecker(String payload){
        //TODO format checker
        //init the format dictionary
        JSONObject s = JSONObject.parseObject(payload);
        //check basic format
        ArrayList<String> basicformat = formatCondition("basic");
        for(int i=0; i<basicformat.size();i++){
            if(!s.containsKey(basicformat.get(i))){
                return false;
            }
        }
        //check specific data format

        return true;
    }

    public ArrayList<String> formatCondition(String check_key){
        JSONObject condition = JSONObject.parseObject("{\"basic\":[{\"name\":\"qid\",\"type\":\"number\"}," + "{\"name\":\"type\",\"type\":\"string\"}," + "{\"name\":\"msg\",\"type\":\"string\"}]," +
                "\"sparttips\":[],\"stext\":[{\"name\":\"required\",\"type\":\"boolean\"}]," +
                "\"sselect\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"}]," +
                "\"sradio\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"}]," +
                "\"schecker\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"},{\"name\":\"maxSelect\",\"type\":\"number\"},{\"name\":\"minSelect\",\"type\":\"number\"}]," +
                "\"srange\":[{\"name\":\"min\",\"type\":\"number\"},{\"name\":\"max\",\"type\":\"number\"},{\"name\":\"step\",\"type\":\"number\"}]}");

        ArrayList<String> name = new ArrayList<String>();


        JSONArray j = (JSONArray) condition.get(check_key);
        for(int i=0;i<j.size();i++){
            JSONObject sub = (JSONObject)j.get(i);
            name.add(sub.get("name").toString());

        }

        return name;
    }
}
