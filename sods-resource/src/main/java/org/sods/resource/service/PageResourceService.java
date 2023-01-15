package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface PageResourceService {
    ResponseResult get(String domain,String language,String path);
    ResponseResult delete(String domain,String language,String path);
    ResponseResult post(String domain,String language,String path, String payload);
    ResponseResult put(String domain,String language,String path, String payload);
}
