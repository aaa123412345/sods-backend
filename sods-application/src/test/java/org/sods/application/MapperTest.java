package org.sods.application;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.resource.domain.PageData;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.PageDataMapper;
import org.sods.resource.mapper.PageRouterMapper;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.security.domain.User;
import org.sods.security.mapper.MenuMapper;
import org.sods.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void TestBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode1);
        System.out.println(encode2);
    }

    @Autowired
    private PageDataMapper pageDataMapper;
    @Test
    public void tesPageMapper(){
        PageData s = pageDataMapper.selectById("1");
        System.out.println(s);
    }

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Autowired
    private PageRouterMapper pageRouterMapper;
    @Test
    public void testSelectPageDataByURLAndLan(){
        String js = pageRouterMapper.selectPageDataByURLAndLan("public/about","eng");
        System.out.println(js);
    }

    @Test
    public  void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(1606651715013095425L);
        System.out.println(list);
    }

    @Test
    public  void aa(){

        System.out.println(1);
    }

    @Test
    public void tryJson(){
        JSONObject condition = JSONObject.parseObject("{\"basic\":[{\"name\":\"qid\",\"type\":\"number\"}," + "{\"name\":\"type\",\"type\":\"string\"}," + "{\"name\":\"msg\",\"type\":\"string\"}]," +
                "\"sparttips\":[],\"stext\":[{\"name\":\"required\",\"type\":\"boolean\"}]," +
                "\"sselect\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"}]," +
                "\"sradio\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"}]," +
                "\"schecker\":[{\"name\":\"required\",\"type\":\"boolean\"},{\"name\":\"option\",\"type\":\"object\"},{\"name\":\"maxSelect\",\"type\":\"number\"},{\"name\":\"minSelect\",\"type\":\"number\"}]," +
                "\"srange\":[{\"name\":\"min\",\"type\":\"number\"},{\"name\":\"max\",\"type\":\"number\"},{\"name\":\"step\",\"type\":\"number\"}]}");
        String check_key = "basic";
        HashMap<String,ArrayList> response = new HashMap<String,ArrayList>();
        ArrayList<String> name = new ArrayList<String>();
        ArrayList<String> type = new ArrayList<String>();

        JSONArray j = (JSONArray) condition.get(check_key);
        for(int i=0;i<j.size();i++){
            JSONObject sub = (JSONObject)j.get(i);
            name.add(sub.get("name").toString());
            type.add(sub.get("type").toString());
        }
        response.put("name",name);
        response.put("type",type);

        System.out.println(response);

    }

    @Autowired
    private SurveyMapper surveyMapper;

    @Test
    public void getAllSurvey(){
        QueryWrapper<Survey> surveyQueryWrapper = new QueryWrapper<>();
        surveyQueryWrapper.eq("delflag",0);
        List<Survey> surveyList = surveyMapper.selectList(surveyQueryWrapper);
        for (int i = 0; i < surveyList.size(); i++) {
            surveyList.get(i).setSurveyFormat("");
        }
        System.out.println(surveyList);
    }

    @Test
    public void putData(){
        //set survey object
        LocalDateTime current = LocalDateTime.now();
        Survey s = new Survey();
        s.setSurveyFormat("bbb");
        s.setCreateUserId(1L);
        s.setUpdateUserId(1L);
        s.setCreateTime(current);
        s.setUpdateTime(current);
        //save to database
        surveyMapper.insert(s);
        System.out.println("Survey is added");
    }
}
