package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.TourguideConfig;
import org.sods.resource.service.TourguideConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TourGuide/configs")
public class TourguideConfigController {

    @Autowired
    TourguideConfigService tourguideConfigService;

    @PostMapping
    public ResponseResult createConfig(@RequestBody TourguideConfig tourguideConfig){
        return tourguideConfigService.createConfig(tourguideConfig);
    }

    @GetMapping
    public ResponseResult getConfig(){
        return tourguideConfigService.getConfig();
    }

    @PutMapping
    public ResponseResult updateConfig(@RequestBody TourguideConfig tourguideConfig){
        return tourguideConfigService.updateConfig(tourguideConfig);
    }

}
