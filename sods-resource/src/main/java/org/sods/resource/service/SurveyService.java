package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface SurveyService {
    ResponseResult get(Long id);
    ResponseResult listAll(Boolean withFormat );
    ResponseResult delete(Long id);
    ResponseResult put(Long id,String payload);
    ResponseResult post(String payload);
}
