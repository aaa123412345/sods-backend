package org.sods.resource.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.sods.resource.service.BoothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/TourGuide")
public class BoothController {

    @Autowired
    BoothService boothService;

    @PostMapping("/booths")
    public ResponseResult createBooth(@RequestBody Booth booth){
        return boothService.createBooth(booth);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PostMapping("/markers/{y}/{x}/{floorPlanID}/booths")
    public ResponseResult createBoothWithMarker(@RequestBody Booth booth, @PathVariable Double y, @PathVariable Double x, @PathVariable Long floorPlanID){
        return boothService.createBoothWithMaker(booth, y, x, floorPlanID);
    }

    @GetMapping("/booths")
    public ResponseResult getAllBooths(
            @RequestParam(name = "floorplanId", required = false) Long floorPlanID,
            @RequestParam(name = "deleteFlag", required = false) Integer deleteFlag
    ){
        return boothService.getAllBooths(floorPlanID, deleteFlag);
    }

    @GetMapping("/booths/{id}")
    public ResponseResult getBoothById(@PathVariable Long id){
        return boothService.getBoothById(id);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PutMapping("/booths/{id}")
    public ResponseResult updateBoothById(@PathVariable Long id, @RequestBody Booth booth) {
        return boothService.updateBoothById(id, booth);
    }
    /**
    public ResponseResult updateBoothById(@PathVariable Integer id, @RequestParam("booth") String boothString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Booth booth = mapper.readValue(boothString, Booth.class);
        return boothService.updateBoothById(id, booth, imageFile);
    }
     **/

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @DeleteMapping("/booths/{id}")
    public ResponseResult deleteBoothById(@PathVariable Long id){
        return boothService.deleteBoothById(id);
    }

}
