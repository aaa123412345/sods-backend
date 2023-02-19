package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothGame;
import org.sods.resource.service.BoothGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/miniGame/boothGame")
public class BoothGameController {

    @Autowired
    BoothGameService boothGameService;

    @PostMapping
    public ResponseResult createBoothGame(@RequestBody BoothGame boothGame){
        return boothGameService.createBoothGame(boothGame);
    }

    @GetMapping
    public ResponseResult getAllBoothGame(
            @RequestParam(name = "boothId", required = false) Integer boothId,
            @RequestParam(name = "gameId", required = false) Integer gameId
    ){
        if(boothId != null)
            return boothGameService.getBoothGameByBoothId(boothId);
        if(gameId != null)
            return boothGameService.getBoothGameByGameId(gameId);
        return boothGameService.getAllBoothGame();
    }

    @PutMapping
    public ResponseResult updateBoothGame(
            @RequestParam(name = "boothId", required = true) Integer boothId,
            @RequestParam(name = "gameId", required = true) Integer gameId,
            @RequestBody BoothGame boothGame
    ){
        return boothGameService.updateBoothGameByGameIdAndBoothId(boothId, gameId, boothGame);
    }

    @DeleteMapping
    public ResponseResult updateBoothGame(
            @RequestParam(name = "boothId", required = true) Integer boothId,
            @RequestParam(name = "gameId", required = true) Integer gameId
    ){
        return boothGameService.deleteFloorPlanByBoothIdAndGameId(boothId, gameId);
    }

}
