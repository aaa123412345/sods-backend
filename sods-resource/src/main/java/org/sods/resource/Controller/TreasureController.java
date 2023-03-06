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
    public ResponseResult getAllARTreasure(@RequestParam(name = "deleteFlag", required = false) Integer deleteFlag){
        return treasureService.getAllARTreasure(deleteFlag);
    }

    @GetMapping("/{id}")
    public ResponseResult getARTreasureById(@PathVariable Long id){
        return treasureService.getARTreasureById(id);
    }

    @PutMapping("/{id}")
    public ResponseResult updateARTreasureById(@PathVariable Long id, @RequestBody Treasure treasure){
        return treasureService.updateARTreasureById(id, treasure);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteARTreasureById(@PathVariable Long id){
        return treasureService.deleteARTreasure(id);
    }


}
