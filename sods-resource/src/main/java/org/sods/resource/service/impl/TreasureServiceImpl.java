package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Treasure;
import org.sods.resource.mapper.TourguideConfigMapper;
import org.sods.resource.mapper.TreasureMapper;
import org.sods.resource.service.TreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TreasureServiceImpl implements TreasureService {


    @Autowired
    TourguideConfigMapper tourguideConfigMapper;

    @Autowired
    TreasureMapper treasureMapper;

    @Override
    public ResponseResult createARTreasure(Treasure treasure) {

        treasureMapper.insert(treasure);
        return new ResponseResult(201,"New AR Treasure Game is created successfully.");
    }

    @Override
    public ResponseResult getAllARTreasure(Integer deleteFlag) {

        Integer delFlag = deleteFlag == null ? 0 : deleteFlag;

        List<Treasure> result = delFlag == 0 ? treasureMapper.selectList(null) : treasureMapper.findDeletedTreasures();
        return new ResponseResult(200, "AR Treasure Game List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARTreasureById(Long id) {

        Treasure result = treasureMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found.");

        return new ResponseResult(200, "AR Treasure Game (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateARTreasureById(Long id, Treasure treasure) {

        Treasure existingARARTreasure = treasureMapper.selectById(id);
        if(existingARARTreasure == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found. ");

        existingARARTreasure.setQuestionEN(treasure.getQuestionEN());
        existingARARTreasure.setQuestionZH(treasure.getQuestionZH());
        existingARARTreasure.setAnswers((treasure.getAnswers()));
        treasureMapper.updateById(existingARARTreasure);
        return new ResponseResult(200, "AR Treasure Game (id: " + id + ") is updated successfully.");
    }

    @Override
    public ResponseResult deleteARTreasure(Long id) {
        Treasure existingARARTreasure = treasureMapper.selectById(id);
        if(existingARARTreasure == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found. ");
        treasureMapper.deleteById(id);
        return new ResponseResult(200,"AR Treasure Game (id: " + id + ") with related markers and booths are deleted successfully. ");
    }
}
