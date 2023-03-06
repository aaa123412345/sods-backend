package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TreasureStatistic;
import org.sods.resource.service.TreausreStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ARGame/treasureStatistic")
public class TreasureStatisticController {

    @Autowired
    TreausreStatisticService treausreStatisticService;

    @PostMapping
    public ResponseResult createARStatistic(@RequestBody TreasureStatistic treasureStatistic){
        return treausreStatisticService.createARStatistic(treasureStatistic);
    }

    @GetMapping
    public ResponseResult getAllARStatistic(){
        return treausreStatisticService.getAllARStatistic();
    }

    @GetMapping("/{id}")
    public ResponseResult getARStatisticById(@PathVariable Long id){
        return treausreStatisticService.getARStatisticById(id);
    }
}
