package org.sods.application;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.resource.domain.PageData;
import org.sods.resource.domain.Survey;
import org.sods.resource.mapper.PageDataMapper;
import org.sods.resource.mapper.SurveyMapper;
import org.sods.security.domain.User;
import org.sods.security.mapper.MenuMapper;
import org.sods.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    @Test
    public void testGetPageDataMapper(){
        String domain = "public";
        String path = "about1";
        String language = "eng";

        //Set the searching requirement
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        pageDataQueryWrapper.eq("delFlag",0);

        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);
        if(Objects.isNull(p)){
            System.out.println("no data");
        }else{
            System.out.println(p);
        }

    }

    @Test
    public void testCreatePageDataMapper(){
        String domain = "public";
        String path = "about";
        String language = "eng";
        String payload = "abc";

        //Set the searching requirement
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData ex = pageDataMapper.selectOne(pageDataQueryWrapper);

        if(!Objects.isNull(ex)){
            System.out.println("Object exist");
        }else{
            PageData p = new PageData();
            p.setDomain(domain);
            p.setPath(path);
            p.setLanguage(language);
            p.setPageData(payload);
            pageDataMapper.insert(p);
            System.out.println("Object create");
        }


    }

    @Test
    public void testUpdatePageDataMapper(){
        String domain = "public";
        String path = "about";
        String language = "eng";
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        pageDataQueryWrapper.eq("delFlag",0);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        p.setDomain(domain);
        p.setPath(path);
        p.setLanguage(language);
        p.setPageData("d");
        pageDataMapper.updateById(p);
    }

    @Test
    public void testDeletePageDataMapper(){
        String domain = "public";
        String path = "about";
        String language = "eng";
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        pageDataMapper.deleteById(p.getPageId());
    }
}
