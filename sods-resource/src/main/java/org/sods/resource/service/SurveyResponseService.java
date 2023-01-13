package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface SurveyResponseService {
    ResponseResult get();
    ResponseResult delete();
    ResponseResult put();
    ResponseResult post();
}
