package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;

public interface ActiveSurveyService {
    ResponseResult get();
    ResponseResult delete();
    ResponseResult put();
    ResponseResult post();
}
