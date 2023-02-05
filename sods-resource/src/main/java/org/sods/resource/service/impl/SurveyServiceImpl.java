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


        Survey target = surveyMapper.selectById(id);
        if(Objects.isNull(target)){
            return new ResponseResult(404,"Failed to get: Survey is not find");
        }

        return new ResponseResult(200,"Survey: "+target.getSurveyId()+" is returned"
                ,JSONObject.parseObject(target.getSurveyFormat()));
    }

    @Override
    public ResponseResult listAll(Boolean withFormat) {

        List<Survey> surveyList = surveyMapper.selectList(null);
        if(!withFormat) {
            for (int i = 0; i < surveyList.size(); i++) {
                surveyList.get(i).setSurveyFormat("");
            }
        }

        return new ResponseResult(200,"Get All",surveyList);
    }

    @Override
    public ResponseResult delete(Long id) {

        //get target survey object
        Survey target = surveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to delete: Survey is not find");
        }

        //save to database
        surveyMapper.deleteById(target.getSurveyId());

        return new ResponseResult(200,"Survey is deleted");
    }

    @Override
    public ResponseResult put(Long id, String payload) {

        //get target survey object
        Survey target = surveyMapper.selectById(id);

        //Check if survey is not exist
        if(Objects.isNull(target)){
            return new ResponseResult(400,"Failed to update: Survey is not find");
        }
        target.setSurveyFormat(payload);
        //save to database
        surveyMapper.updateById(target);
        return new ResponseResult(200,"Survey is update");
    }

    @Override
    public ResponseResult post(String payload) {
        //get specific data from json
        JSONObject tmp = JSONObject.parseObject(payload);
        JSONObject info = tmp.getJSONObject("info");
        //set survey object
        Survey s = new Survey();
        s.setSurveyFormat(payload);
        s.setSurveyTitle(info.getString("title"));
        s.setSurveyType(info.getString("type"));


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
