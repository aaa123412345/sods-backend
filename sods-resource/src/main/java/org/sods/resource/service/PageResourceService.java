package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface PageResourceService {
    ResponseResult get(String subDomain,String language,String pageName);
    ResponseResult delete(String subDomain,String language,String pageName);
    ResponseResult post(String subDomain,String language,String pageName, String JsonData);
    ResponseResult put(String subDomain,String language,String pageName, String JsonData);
}
