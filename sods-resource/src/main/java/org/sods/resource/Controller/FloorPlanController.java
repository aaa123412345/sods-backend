package org.sods.resource.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.FloorPlan;
import org.sods.resource.domain.Marker;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.FloorPlanMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/tourguide-floorplans")
public class FloorPlanController {

    @Autowired
    FloorPlanMapper floorPlanMapper;

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    BoothMapper boothMapper;

    @PostMapping
    public ResponseResult createFloorPlan(@RequestParam("floorplan") String floorPlanString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FloorPlan floorPlan = mapper.readValue(floorPlanString, FloorPlan.class);
        // upload file to aws
        // get url from cdn
        // floorPlan.setImageUrl(cdn);
        floorPlanMapper.insert(floorPlan);
        return new ResponseResult(200, "OK", floorPlan);
    }

    @GetMapping
    public ResponseResult getAllFloorPlan(){
        List<FloorPlan> result = floorPlanMapper.selectList(null);
        return new ResponseResult(200, "OK", result);
    }

    @GetMapping("/{id}")
    public ResponseResult getFloorPlanById(@PathVariable Integer id){
        FloorPlan floorPlan = floorPlanMapper.selectById(id);
        return new ResponseResult(200, "OK", floorPlan);
    }

    @PutMapping("/{id}")
    public ResponseResult updateFloorPlanById(@PathVariable Integer id, @RequestParam("floorplan") String floorPlanString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        FloorPlan floorPlan = mapper.readValue(floorPlanString, FloorPlan.class);
        floorPlan.setId(id);
        // upload file to aws
        // get url from cdn
        //floorPlan.setImageUrl("http://sim-aws-cdn.com/testing-image-"+num+".png");
        floorPlanMapper.updateById(floorPlan);
        return new ResponseResult(200, "OK", floorPlan);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteFloorPlanById(@PathVariable Integer id){

        int result = floorPlanMapper.deleteById(id);

        if(result == 1){

            QueryWrapper query = new QueryWrapper<>();
            query.eq("fk_floorplan_id", id);

            List<Marker> markerList = markerMapper.selectList(query);
            for(int i = 0; i < markerList.size(); i++){
                Marker marker = markerList.get(i);
                if(marker.getBoothID() != null)
                    boothMapper.deleteById(marker.getBoothID());
                markerMapper.deleteMarkerByCID(marker.getY(), marker.getX(), marker.getFloorPlanID());
            }

        }

        return new ResponseResult(200, "OK", null);

    }

}
