package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.PageData;
import org.sods.resource.mapper.PageDataMapper;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PageResourceServiceImpl implements PageResourceService {
    private final String baseLang = "eng";

    @Autowired
    PageDataMapper pageDataMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public ResponseResult get(String domain, String language, String path) {
        //Set the searching requirement
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        //pageDataQueryWrapper.eq("language",language);

        List<PageData> p = pageDataMapper.selectList(pageDataQueryWrapper);

        AtomicReference<PageData> target = new AtomicReference<>(new PageData());
        AtomicReference<PageData> targetEng = new AtomicReference<>(new PageData());
        AtomicReference<Boolean> pageLangExist = new AtomicReference<>(false);

        p.forEach(e->{
            if(e.getLanguage().equals(language)){
                target.set(e);
                pageLangExist.set(true);
            }
            if(e.getLanguage().equals(baseLang)){
                targetEng.set(e);
            }
        });

        //if data not exist, show the error
        if(Objects.isNull(p)){
            return  new ResponseResult<>(404,"Get Error: Page is not exist");
        }
        if(pageLangExist.get()){
            return new ResponseResult<>(200,"Get Success: Page data is returned",
                JSONObject.parseObject(target.get().getPageData()));
        }else{
            return new ResponseResult<>(200,"Get Success: Page data is returned. But Language is not exist",
                    JSONObject.parseObject(targetEng.get().getPageData()));
        }
    }

    @Override
    public ResponseResult delete(String domain, String language, String path) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        if(Objects.isNull(p)){
            return new ResponseResult<>(400,"Delete Error: Page is not exist");
        }
        removeCache(domain, language, path);

        pageDataMapper.deleteById(p.getPageId());
        return new ResponseResult<>(200,"Delete Success: Page is deleted");
    }

    @Override
    public ResponseResult post(String domain, String language, String path, String payload) {
        //Set the searching requirement
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData ex = pageDataMapper.selectOne(pageDataQueryWrapper);

        if(!Objects.isNull(ex)){
            return  new ResponseResult<>(400,"Post Error: Page exist");
        }
        PageData p = new PageData();
        p.setDomain(domain);
        p.setPath(path);
        p.setLanguage(language);
        p.setPageData(payload);
        pageDataMapper.insert(p);

        return new ResponseResult<>(200,"Post Success: Page is created");
    }

    @Override
    public ResponseResult put(String domain, String language, String path, String payload) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        //remove redis cache if exist
        removeCache(domain,language,path);

        if(Objects.isNull(p)){
            return new ResponseResult<>(400,"Put Error: Page is not exist");
        }

        p.setDomain(domain);
        p.setPath(path);
        p.setLanguage(language);
        p.setPageData(payload);
        pageDataMapper.updateById(p);

        return new ResponseResult<>(200,"Put Success: Page is updated");
    }

    @Override
    public ResponseResult forceUpdate(String domain, String language, String path, String payload) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        //remove redis cache if exist
        removeCache(domain,language,path);

        if(Objects.isNull(p)){
            //create mode
            p = new PageData();
            p.setDomain(domain);
            p.setPath(path);
            p.setLanguage(language);
            p.setPageData(payload);
            pageDataMapper.insert(p);
            return new ResponseResult<>(200,"Put Success: Page is created");
        }
        //update mode
        p.setDomain(domain);
        p.setPath(path);
        p.setLanguage(language);
        p.setPageData(payload);
        pageDataMapper.updateById(p);

        return new ResponseResult<>(200,"Put Success: Page is updated");
    }

    @Override
    public ResponseResult checkResource(String domain, String language, String path) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        Map<String, Object> map = new HashMap<>();

        if(Objects.isNull(p)){
            map.put("exist", false);
            return new ResponseResult<>(HttpStatus.OK.value(),"Check Error: Page is not exist",map);
        }
        map.put("exist", true);
        return new ResponseResult<>(HttpStatus.OK.value(), "Check Success: Page is exist",map);
    }

    @Override
    public ResponseResult getPages(String domain, String language, String path) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        if(Objects.nonNull(domain)){
            pageDataQueryWrapper.eq("domain",domain);
        }
        if(Objects.nonNull(language)){
            pageDataQueryWrapper.eq("language",language);
        }
        if(Objects.nonNull(path)){
            pageDataQueryWrapper.like("path","%"+path+"%");
        }
        pageDataQueryWrapper.select("domain","path","language");
        List<PageData> p = pageDataMapper.selectList(pageDataQueryWrapper);


        if(Objects.isNull(p)){
            return  new ResponseResult<>(404,"Get Error: Page is not exist");
        }

        //convert to map
        List<Map> results = new ArrayList<>();
        p.forEach(e->{
            Map<String,Object> result = new HashMap<>();
            result.put("domain",e.getDomain());
            result.put("path",e.getPath());
            result.put("language",e.getLanguage());
            results.add(result);
        });

        //return the result
        return new ResponseResult<>(200,"Get Success: Page data is returned",results);


    }

    public void removeCache(String domain, String language, String path){
        String cacheKey = "CACHE:"+domain+":/rest/"+domain+"/"+language+"/"+path;
        if(redisTemplate.hasKey(cacheKey)) {
            redisTemplate.delete(cacheKey);
        }
    }

    @Override
    public Boolean makeBackup(String domain, String language, String path) {
        QueryWrapper<PageData> pageDataQueryWrapper = new QueryWrapper<>();
        pageDataQueryWrapper.eq("domain",domain);
        pageDataQueryWrapper.eq("path",path);
        pageDataQueryWrapper.eq("language",language);
        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        if(Objects.isNull(p)){
            return false;
        }

        QueryWrapper<PageData> pageDataQueryWrapper2 = new QueryWrapper<>();
        pageDataQueryWrapper2.eq("domain",domain);
        pageDataQueryWrapper2.eq("path",path+"_backup");
        pageDataQueryWrapper2.eq("language",language);
        PageData p2 = pageDataMapper.selectOne(pageDataQueryWrapper2);

        if(Objects.isNull(p2)){
            p.setPageId(null);
            p.setPath(p.getPath()+"_backup");
            pageDataMapper.insert(p);
        }else {
            p2.setPageData(p.getPageData());
            pageDataMapper.updateById(p2);
        }

        return true;
    }
}
