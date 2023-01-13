package org.sods.resource.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Marker;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tourguide-markers")
public class MarkerController {

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    BoothMapper boothMapper;

    @PostMapping
    public ResponseResult createMarker(@RequestBody Marker marker){
        markerMapper.insert(marker);
        return new ResponseResult(200, "OK", marker);
    }

    @GetMapping
    public ResponseResult getAllMarkers(@RequestParam(name = "floorplan-id", required = false) Integer floorPlanID, @RequestParam(name = "booth-id", required = false) Integer boothID){

        QueryWrapper<Marker> query = new QueryWrapper<>();
        if(floorPlanID != null)
            query.eq("fk_floorplan_id", floorPlanID);
        if(boothID != null)
            query.eq("fk_booth_id", boothID);

        List<Marker> result = markerMapper.selectList(query);
        return new ResponseResult(200, "OK", result);
    }

    @GetMapping("/{y}/{x}/{floorPlanID}")
    public ResponseResult getMarkerById(@PathVariable Double y, @PathVariable Double x, @PathVariable Integer floorPlanID){
        Marker result = markerMapper.findMarkerByCID(y, x, floorPlanID);
        return new ResponseResult(200, "OK", result);
    }

    @DeleteMapping("/{y}/{x}/{floorPlanID}")
    public ResponseResult deleteMarkerById(@PathVariable Double y, @PathVariable Double x, @PathVariable Integer floorPlanID){
        Marker result = markerMapper.deleteMarkerByCID(y, x, floorPlanID);
        if(result != null && result.getBoothID() != null)
            boothMapper.deleteById(result.getBoothID());
        return new ResponseResult(200, "OK", null);
    }

}
