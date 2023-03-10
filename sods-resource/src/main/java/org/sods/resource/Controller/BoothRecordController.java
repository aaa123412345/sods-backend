package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.BoothRecord;
import org.sods.resource.service.BoothRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TourGuide/boothRecords")
public class BoothRecordController {

    @Autowired
    BoothRecordService boothRecordService;

    @PostMapping
    public ResponseResult createRecord(@RequestBody BoothRecord boothRecord){
        return boothRecordService.createRecord(boothRecord);
    }

    @GetMapping
    public ResponseResult getAllRecords(@RequestParam(name = "userId", required = false) Long userId){
        return boothRecordService.getAllRecords(userId);
    }

    @PutMapping
    public ResponseResult updateRecordByUserIdAndBoothId(@RequestBody BoothRecord boothRecord){
        return boothRecordService.updateRecordByUserIdAndBoothId(boothRecord);
    }

}
