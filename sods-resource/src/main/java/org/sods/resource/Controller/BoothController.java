package org.sods.resource.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class BoothController {

    @Autowired
    BoothMapper boothMapper;

    @Autowired
    MarkerMapper markerMapper;

    @PostMapping("/tourguide-markers/{y}/{x}/{floorPlanID}/tourguide-booths")
    public ResponseResult createBooth(@RequestBody Booth booth, @PathVariable Double y, @PathVariable Double x, @PathVariable Integer floorPlanID){
        int result = boothMapper.insert(booth);
        Integer newBoothID = booth.getId();
        if(result == 1)
            markerMapper.updateBoothOfMarker(y, x, floorPlanID, newBoothID);
        return new ResponseResult(200, "OK", booth);
    }

    @GetMapping("/tourguide-booths")
    public ResponseResult getAllBooths(@RequestParam(name = "floorplan-id", required = false) Integer floorPlanID){
        List<Booth> result = floorPlanID != null ? boothMapper.findBoothsByFloorPlanID(floorPlanID) : boothMapper.selectList(null);
        return new ResponseResult(200, "OK", result);
    }

    @GetMapping("/tourguide-booths/{id}")
    public ResponseResult getBoothById(@PathVariable Integer id){
        Booth booth = boothMapper.selectById(id);
        return new ResponseResult(200, "OK", booth);
    }

    @PutMapping("/tourguide-booths/{id}")
    public ResponseResult updateBoothById(@PathVariable Integer id, @RequestParam("booth") String boothString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Booth booth = mapper.readValue(boothString, Booth.class);
        // upload file to aws
        // get url from cdn
        //booth.setImageUrl(cdn);
        booth.setId(id);
        boothMapper.updateById(booth);
        return new ResponseResult(200, "OK", booth);
    }

    @DeleteMapping("/tourguide-booths/{id}")
    public ResponseResult deleteBoothById(@PathVariable Integer id){
        boothMapper.deleteById(id);
        return new ResponseResult(200, "OK", null);
    }

}
