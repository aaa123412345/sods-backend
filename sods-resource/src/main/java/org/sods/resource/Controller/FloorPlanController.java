package org.sods.resource.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.FloorPlan;
import org.sods.resource.service.FloorPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/tourguide/floorplans")
public class FloorPlanController {

    @Autowired
    FloorPlanService floorPlanService;

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PostMapping
    public ResponseResult createFloorPlan(@RequestBody FloorPlan floorPlan) {
        return floorPlanService.createFloorPlan(floorPlan);
    }
    /**
    public ResponseResult createFloorPlan(@RequestParam("floorplan") String floorPlanString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FloorPlan floorPlan = mapper.readValue(floorPlanString, FloorPlan.class);
        return floorPlanService.createFloorPlan(floorPlan, imageFile);
    }
     **/

    @GetMapping
    public ResponseResult getAllFloorPlan(){
        return floorPlanService.getAllFloorPlan();
    }

    @GetMapping("/{id}")
    public ResponseResult getFloorPlanById(@PathVariable Integer id){
        return floorPlanService.getFloorPlanById(id);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PutMapping("/{id}")
    public ResponseResult updateFloorPlanById(@PathVariable Integer id, @RequestBody FloorPlan floorPlan) {
        return floorPlanService.updateFloorPlanById(id, floorPlan);
    }
    /**
    public ResponseResult updateFloorPlanById(@PathVariable Integer id, @RequestParam("floorplan") String floorPlanString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        FloorPlan floorPlan = mapper.readValue(floorPlanString, FloorPlan.class);
        return floorPlanService.updateFloorPlanById(id, floorPlan, imageFile);
    }
     **/

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @DeleteMapping("/{id}")
    public ResponseResult deleteFloorPlanById(@PathVariable Integer id){
        return floorPlanService.deleteFloorPlanById(id);
    }

}
