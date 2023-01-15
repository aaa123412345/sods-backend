package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.PageData;
import org.sods.resource.service.PageResourceService;
import org.springframework.stereotype.Service;

@Service
public class PageResourceServiceImpl implements PageResourceService {


    @Override
    public ResponseResult get(String subDomain, String language, String pageName) {
        return null;
    }

    @Override
    public ResponseResult delete(String subDomain, String language, String pageName) {
        String url = subDomain+"/"+pageName;
        QueryWrapper<PageData> pageRouterQueryWrapper = new QueryWrapper<>();
        pageRouterQueryWrapper.eq("url",url);
        pageRouterQueryWrapper.eq("language",language);
        pageRouterQueryWrapper.eq("delFlag",0);


        return new ResponseResult(200, "Page is deleted");
    }

    @Override
    public ResponseResult post(String subDomain, String language, String pageName, String JsonData) {
        String url = subDomain+"/"+pageName;
        QueryWrapper<PageData> pageRouterQueryWrapper = new QueryWrapper<>();
        pageRouterQueryWrapper.eq("url",url);
        pageRouterQueryWrapper.eq("language",language);
        pageRouterQueryWrapper.eq("delFlag",0);
        return null;
    }

    @Override
    public ResponseResult put(String subDomain, String language, String pageName, String JsonData) {
        String url = subDomain+"/"+pageName;
        QueryWrapper<PageData> pageRouterQueryWrapper = new QueryWrapper<>();
        pageRouterQueryWrapper.eq("url",url);
        pageRouterQueryWrapper.eq("language",language);
        pageRouterQueryWrapper.eq("delFlag",0);



        return null;
    }
}
