package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothRecord;
import org.sods.resource.mapper.BoothRecordMapper;
import org.sods.resource.service.BoothRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoothRecordServiceImpl implements BoothRecordService {

    @Autowired
    BoothRecordMapper boothRecordMapper;

    @Override
    public ResponseResult createRecord(BoothRecord boothRecord) {

        if(boothRecord.getUserId() > 0){
            QueryWrapper query = new QueryWrapper<>();
            query.eq("user_id", boothRecord.getUserId());
            query.eq("booth_id", boothRecord.getBoothId());
            List<BoothRecord> result = boothRecordMapper.selectList(query);
            if(result.size() > 0)
                return new ResponseResult(409, "Failed: Booth Record exists");
        }

        boothRecordMapper.insert(boothRecord);
        return new ResponseResult(201, "Booth Record is created successfully");
    }

    @Override
    public ResponseResult getAllRecords(Long userId) {

        QueryWrapper query = new QueryWrapper<>();

        if(userId != null)
            query.eq("user_id", userId);

        List<BoothRecord> result = boothRecordMapper.selectList(userId != null ? query : null);
        return new ResponseResult(200, "Booth Record List is retrieved successfully", result);
    }

    @Override
    public ResponseResult updateRecordByUserIdAndBoothId(BoothRecord boothRecord) {

        QueryWrapper query = new QueryWrapper<>();
        query.eq("user_id", boothRecord.getUserId());
        query.eq("booth_id", boothRecord.getBoothId());

        List<BoothRecord> result = boothRecordMapper.selectList(query);

        if(result.size() == 0)
            return new ResponseResult(404, "Failed: Booth Record (user id: " + boothRecord.getUserId() + " & booth id: " + boothRecord.getBoothId() + " ) is not found. ");

        BoothRecord existingRecord = result.get(0);

        boolean isUpdateStamp = existingRecord.getIsGotStamp() == 0 && boothRecord.getIsGotStamp() != null;
        boolean isUpdateRating = existingRecord.getRatingScore() == null && boothRecord.getRatingScore() != null;

        if(isUpdateStamp || isUpdateRating){
            if(isUpdateStamp)
                existingRecord.setIsGotStamp(boothRecord.getIsGotStamp());
            if(isUpdateRating)
                existingRecord.setRatingScore(boothRecord.getRatingScore());
            boothRecordMapper.update(existingRecord, query);
        }

        return new ResponseResult(200, "Booth Record (user id: " + boothRecord.getUserId() + " & booth id: " + boothRecord.getBoothId() + " ) is updated successfully");
    }

}
