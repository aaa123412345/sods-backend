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
    public ResponseResult get(String subDomain, String language, String pageName) {
        String url = subDomain+"/"+pageName;
        System.out.println(url);
        String s = pageRouterMapper.selectPageDataByURLAndLan(url,language);
        if(StringUtils.hasText(s)) {
            return new ResponseResult(200, "ready", JSONObject.parseObject(s));
        }else{
            return new ResponseResult(404, "Page not found");
        }
    }

    @Override
    public ResponseResult delete(String subDomain, String language, String pageName) {
        return null;
    }

    @Override
    public ResponseResult post(String subDomain, String language, String pageName, String JsonData) {
        return null;
    }

    @Override
    public ResponseResult put(String subDomain, String language, String pageName, String JsonData) {
        return null;
    }
}
