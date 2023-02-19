package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARStatistic;
import org.sods.resource.domain.FloorPlan;
import org.sods.resource.mapper.ARStatisticMapper;
import org.sods.resource.service.ARStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ARStatisticServiceImpl implements ARStatisticService {

    @Autowired
    ARStatisticMapper arStatisticMapper;
    @Override
    public ResponseResult createARStatistic(ARStatistic arStatistic) {
        arStatisticMapper.insert(arStatistic);
        return new ResponseResult(201,"New statistic for AR Treasure Game is created successfully.");
    }

    @Override
    public ResponseResult getAllARStatistic() {
        List<ARStatistic> result = arStatisticMapper.selectList(null);
        return new ResponseResult(200, "List of statistic for AR Treasure Game is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARStatisticById(Integer id) {

        ARStatistic result = arStatisticMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: AR Treasure Game Statistic (id: " + id + ") is not found.");

        return new ResponseResult(200, "AR Treasure Game Statistic (id: " + id + ") is retrieved successfully. ", result);

    }
}
