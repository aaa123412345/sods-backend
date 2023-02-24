package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Treasure;
import org.sods.resource.mapper.TreasureMapper;
import org.sods.resource.service.TreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreasureServiceImpl implements TreasureService {

    @Autowired
    TreasureMapper treasureMapper;

    @Override
    public ResponseResult createARTreasure(Treasure treasure) {
        treasureMapper.insert(treasure);
        return new ResponseResult(201,"New AR Treasure Game is created successfully.");
    }

    @Override
    public ResponseResult getAllARTreasure() {
        List<Treasure> result = treasureMapper.selectList(null);
        return new ResponseResult(200, "AR Treasure Game List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARTreasureById(Integer id) {

        Treasure result = treasureMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found.");

        return new ResponseResult(200, "AR Treasure Game (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateARTreasureById(Integer id, Treasure treasure) {

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
    public ResponseResult deleteARTreasure(Integer id) {
        Treasure existingARARTreasure = treasureMapper.selectById(id);
        if(existingARARTreasure == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found. ");
        treasureMapper.deleteById(id);
        return new ResponseResult(200,"AR Treasure Game (id: " + id + ") with related markers and booths are deleted successfully. ");
    }
}
