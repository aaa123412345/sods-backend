package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TreasureStatistic;
import org.sods.resource.mapper.TreasureStatisticMapper;
import org.sods.resource.service.TreausreStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasureStatisticServiceImpl implements TreausreStatisticService {

    @Autowired
    TreasureStatisticMapper treasureStatisticMapper;
    @Override
    public ResponseResult createARStatistic(TreasureStatistic treasureStatistic) {
        treasureStatisticMapper.insert(treasureStatistic);
        return new ResponseResult(201,"New statistic for AR Treasure Game is created successfully.");
    }

    @Override
    public ResponseResult getAllARStatistic() {
        List<TreasureStatistic> result = treasureStatisticMapper.selectList(null);
        return new ResponseResult(200, "List of statistic for AR Treasure Game is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARStatisticById(Long id) {

        TreasureStatistic result = treasureStatisticMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: AR Treasure Game Statistic (id: " + id + ") is not found.");

        return new ResponseResult(200, "AR Treasure Game Statistic (id: " + id + ") is retrieved successfully. ", result);

    }
}
