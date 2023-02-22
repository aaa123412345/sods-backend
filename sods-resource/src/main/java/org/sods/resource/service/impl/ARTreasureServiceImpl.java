package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARTreasure;
import org.sods.resource.mapper.ARTreasureMapper;
import org.sods.resource.service.ARTreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ARTreasureServiceImpl implements ARTreasureService {

    @Autowired
    ARTreasureMapper arTreasureMapper;

    @Override
    public ResponseResult createARTreasure(ARTreasure arTreasure) {
        arTreasureMapper.insert(arTreasure);
        return new ResponseResult(201,"New AR Treasure Game is created successfully.");
    }

    @Override
    public ResponseResult getAllARTreasure() {
        List<ARTreasure> result = arTreasureMapper.selectList(null);
        return new ResponseResult(200, "AR Treasure Game List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARTreasureById(Integer id) {

        ARTreasure result = arTreasureMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found.");

        return new ResponseResult(200, "AR Treasure Game (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateARTreasureById(Integer id, ARTreasure arTreasure) {

        ARTreasure existingARTreasure = arTreasureMapper.selectById(id);
        if(existingARTreasure == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found. ");

        existingARTreasure.setQuestionEN(arTreasure.getQuestionEN());
        existingARTreasure.setQuestionZH(arTreasure.getQuestionZH());
        existingARTreasure.setAnswers((arTreasure.getAnswers()));
        arTreasureMapper.updateById(existingARTreasure);
        return new ResponseResult(200, "AR Treasure Game (id: " + id + ") is updated successfully.");
    }

    @Override
    public ResponseResult deleteARTreasure(Integer id) {
        ARTreasure existingARTreasure = arTreasureMapper.selectById(id);
        if(existingARTreasure == null)
            return new ResponseResult(404, "Failed: AR Treasure Game (id: " + id + ") is not found. ");
        arTreasureMapper.deleteById(id);
        return new ResponseResult(200,"AR Treasure Game (id: " + id + ") with related markers and booths are deleted successfully. ");
    }
}
