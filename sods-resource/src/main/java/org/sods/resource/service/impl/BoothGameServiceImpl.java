package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothGame;
import org.sods.resource.mapper.BoothGameMapper;
import org.sods.resource.service.BoothGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoothGameServiceImpl implements BoothGameService {

    @Autowired
    BoothGameMapper boothGameMapper;

    @Override
    public ResponseResult createBoothGame(BoothGame boothGame) {
        boothGameMapper.insert(boothGame);
        return new ResponseResult(201,"Booth Game is created successfully.");
    }

    @Override
    public ResponseResult getAllBoothGame() {
        List<BoothGame> result = boothGameMapper.selectList(null);
        return new ResponseResult(200, "Booth Game List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getBoothGameByBoothId(Integer id) {

        QueryWrapper query = new QueryWrapper<>();
        query.eq("booth_id", id);
        List<BoothGame> result = boothGameMapper.selectList(query);
        return new ResponseResult(200, "Booth Game List ( with booth id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult getBoothGameByGameId(Integer id) {
        QueryWrapper query = new QueryWrapper<>();
        query.eq("game_id", id);
        List<BoothGame> result = boothGameMapper.selectList(query);
        return new ResponseResult(200, "Booth Game List ( with game id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateBoothGameByGameIdAndBoothId(Integer boothId, Integer gameId, BoothGame boothGame) {

        QueryWrapper query = new QueryWrapper<>();
        query.eq("game_id", gameId);
        query.eq("booth_id", boothId);
        List<BoothGame> result = boothGameMapper.selectList(query);

        if(result.size() == 0)
            return new ResponseResult(404, "Failed: Booth Game (with booth id: " + boothId + " & game id: " + gameId + ") is not found.");

        boothGameMapper.updateBoothGameByGameIdAndBoothId(gameId, boothId, boothGame.getGameId(), boothGame.getBoothId());
        return new ResponseResult(200, "Booth Game is updated successfully. ");
    }

    @Override
    public ResponseResult deleteFloorPlanByBoothIdAndGameId(Integer boothId, Integer gameId) {

        QueryWrapper query = new QueryWrapper<>();
        query.eq("game_id", gameId);
        query.eq("booth_id", boothId);
        List<BoothGame> result = boothGameMapper.selectList(query);

        if(result.size() == 0)
            return new ResponseResult(404, "Failed: Booth Game (with booth id: " + boothId + " & game id: " + gameId + ") is not found.");

        boothGameMapper.deleteBoothGameByGameIdAndBoothId(gameId, boothId);
        return new ResponseResult(200, "Booth Game is deleted successfully. ");
    }
}
