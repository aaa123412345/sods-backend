package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Treasure;
import org.sods.resource.service.TreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ARGame/treasures")
public class TreasureController {

    @Autowired
    TreasureService treasureService;

    @PostMapping
    public ResponseResult createARTreasure(@RequestBody Treasure treasure){
        return treasureService.createARTreasure(treasure);
    }

    @GetMapping
    public ResponseResult getAllARTreasure(){
        return treasureService.getAllARTreasure();
    }

    @GetMapping("/{id}")
    public ResponseResult getARTreasureById(@PathVariable Integer id){
        return treasureService.getARTreasureById(id);
    }

    @PutMapping("/{id}")
    public ResponseResult updateARTreasureById(@PathVariable Integer id, @RequestBody Treasure treasure){
        return treasureService.updateARTreasureById(id, treasure);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteARTreasureById(@PathVariable Integer id){
        return treasureService.deleteARTreasure(id);
    }


}
