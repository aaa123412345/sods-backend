package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARStatistic;
import org.sods.resource.service.ARStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/miniGame/arStatistic")
public class ARStatisticController {

    @Autowired
    ARStatisticService arStatisticService;

    @PostMapping
    public ResponseResult createARStatistic(@RequestBody ARStatistic arStatistic){
        return arStatisticService.createARStatistic(arStatistic);
    }

    @GetMapping
    public ResponseResult getAllARStatistic(){
        return arStatisticService.getAllARStatistic();
    }

    @GetMapping("/{id}")
    public ResponseResult getARStatisticById(@PathVariable Integer id){
        return arStatisticService.getARStatisticById(id);
    }
}
