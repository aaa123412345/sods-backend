package org.sods.resource.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.mapper.PageRouterMapper;
import org.sods.resource.service.PageResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PageResourceServiceImpl implements PageResourceService {
    @Autowired
    private PageRouterMapper pageRouterMapper;

    @Override
    public ResponseResult getResource(String subDomain, String language, String pageName) {
        String s = pageRouterMapper.selectPageDataByURLAndLan(subDomain+"/"+pageName,language);
        if(StringUtils.hasText(s)) {
            return new ResponseResult(200, "ready", JSONObject.parseObject(s));
        }else{
            return new ResponseResult(404, "Page not found");
        }
    }

    @Override
    public ResponseResult deleteResource(String subDomain, String language, String pageName) {
        return null;
    }

    @Override
    public ResponseResult createResource(String subDomain, String language, String pageName, String JsonData) {
        return null;
    }

    @Override
    public ResponseResult updateResource(String subDomain, String language, String pageName, String JsonData) {
        return null;
    }
}
