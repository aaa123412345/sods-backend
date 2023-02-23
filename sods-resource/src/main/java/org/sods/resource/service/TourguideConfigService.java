package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TourguideConfig;

public interface TourguideConfigService {

    ResponseResult createConfig(TourguideConfig tourguideConfig);

    ResponseResult getConfig();

    ResponseResult updateConfig(Integer id, TourguideConfig tourguideConfig);

}
