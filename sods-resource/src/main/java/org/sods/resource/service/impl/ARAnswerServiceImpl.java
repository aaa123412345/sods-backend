package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARAnswer;
import org.sods.resource.mapper.ARAnswerMapper;
import org.sods.resource.service.ARAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ARAnswerServiceImpl implements ARAnswerService {

    @Autowired
    ARAnswerMapper arAnswerMapper;
    @Override
    public ResponseResult createARAnswer(ARAnswer arAnswer) {
        arAnswerMapper.insert(arAnswer);
        return new ResponseResult(201,"New answer for AR treasure game is created successfully.");
    }

    @Override
    public ResponseResult getAllARAnswer() {
        List<ARAnswer> result = arAnswerMapper.selectList(null);
        return new ResponseResult(200, "Answer List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARAnswerById(Integer id) {

        ARAnswer result = arAnswerMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Answer (id: " + id + ") is not found.");

        return new ResponseResult(200, "Answer (id: " + id + ") is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getARAnswerByTreasureId(Integer id) {

        QueryWrapper query = new QueryWrapper<>();
        query.eq("treasure_id", id);
        List<ARAnswer> result = arAnswerMapper.selectList(query);
        if(result == null)
            return new ResponseResult(404, "Failed: Answers (with treasure id: " + id + ") is not found.");

        return new ResponseResult(200, "Answers (with treasure id: " + id + ") is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult deleteARAnswerById(Integer id) {

        ARAnswer arAnswer = arAnswerMapper.selectById(id);
        if(arAnswer == null)
            return new ResponseResult(404, "Failed: Answer (id: " + id + ") is not found. ");

        arAnswerMapper.deleteById(id);

        return new ResponseResult(200,"Answer (id: " + id + ") with related markers and booths are deleted successfully. ");
    }
}
