package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.RedisCache;
import org.sods.resource.domain.PageData;
import org.sods.resource.mapper.PageDataMapper;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PageResourceServiceImpl implements PageResourceService {
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
        pageDataQueryWrapper.eq("language",language);

        PageData p = pageDataMapper.selectOne(pageDataQueryWrapper);

        //if data not exist, show the error
        if(Objects.isNull(p)){
            return  new ResponseResult<>(404,"Get Error: Page is not exist");
        }

        return  new ResponseResult<>(200,"Get Success: Page data is returned",
                JSONObject.parseObject(p.getPageData()));
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
