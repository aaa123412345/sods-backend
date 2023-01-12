package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface PageResourceService {
    ResponseResult getResource(String subDomain,String language,String pageName);
    ResponseResult deleteResource(String subDomain,String language,String pageName);
    ResponseResult createResource(String subDomain,String language,String pageName, String JsonData);
    ResponseResult updateResource(String subDomain,String language,String pageName, String JsonData);
}
