package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARTreasure;
import org.sods.resource.service.ARTreasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minigame/arTreasures")
public class ARTreasureController {

    @Autowired
    ARTreasureService arTreasureService;

    @PostMapping
    public ResponseResult createARTreasure(@RequestBody ARTreasure arTreasure){
        return arTreasureService.createARTreasure(arTreasure);
    }

    @GetMapping
    public ResponseResult getAllARTreasure(){
        return arTreasureService.getAllARTreasure();
    }

    @GetMapping("/{id}")
    public ResponseResult getARTreasureById(@PathVariable Integer id){
        return arTreasureService.getARTreasureById(id);
    }

    @PutMapping("/{id}")
    public ResponseResult updateARTreasureById(@PathVariable Integer id, @RequestBody ARTreasure arTreasure){
        return arTreasureService.updateARTreasureById(id, arTreasure);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteARTreasureById(@PathVariable Integer id){
        return arTreasureService.deleteARTreasure(id);
    }


}
