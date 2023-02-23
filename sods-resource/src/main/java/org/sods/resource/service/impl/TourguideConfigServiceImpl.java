package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TourguideConfig;
import org.sods.resource.mapper.TourguideConfigMapper;
import org.sods.resource.service.TourguideConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourguideConfigServiceImpl implements TourguideConfigService {

    @Autowired
    TourguideConfigMapper tourguideConfigMapper;

    @Override
    public ResponseResult createConfig(TourguideConfig tourguideConfig) {
        tourguideConfigMapper.insert(tourguideConfig);
        return new ResponseResult(201, "New config is created successfully. ");
    }

    @Override
    public ResponseResult getConfig() {
        List<TourguideConfig> result = tourguideConfigMapper.selectList(null);

        if(result == null)
            return new ResponseResult(404, "Failed: Config is not found.");

        return new ResponseResult(200, "Config is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult updateConfig(Integer id, TourguideConfig tourguideConfig) {
        TourguideConfig existingConfig = tourguideConfigMapper.selectById(id);
        if(existingConfig == null)
            return new ResponseResult(404, "Failed: Config is not found. ");

        existingConfig.setThemeColor(tourguideConfig.getThemeColor());
        existingConfig.setOpendayDate(tourguideConfig.getOpendayDate());
        tourguideConfigMapper.updateById(existingConfig);
        return new ResponseResult(200, "Config is updated successfully.");
    }
}
