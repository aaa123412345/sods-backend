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
@RequestMapping("/tourguide")
public class BoothController {

    @Autowired
    BoothService boothService;

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PostMapping("/markers/{y}/{x}/{floorPlanID}/booths")
    public ResponseResult createBooth(@RequestBody Booth booth, @PathVariable Double y, @PathVariable Double x, @PathVariable Integer floorPlanID){
        return boothService.createBooth(booth, y, x, floorPlanID);
    }

    @GetMapping("/booths")
    public ResponseResult getAllBooths(@RequestParam(name = "floorplan_id", required = false) Integer floorPlanID){
        return boothService.getAllBooths(floorPlanID);
    }

    @GetMapping("/booths/{id}")
    public ResponseResult getBoothById(@PathVariable Integer id){
        return boothService.getBoothById(id);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PutMapping("/booths/{id}")
    public ResponseResult updateBoothById(@PathVariable Integer id, @RequestParam("booth") String boothString, @RequestParam("image") MultipartFile imageFile) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Booth booth = mapper.readValue(boothString, Booth.class);
        return boothService.updateBoothById(id, booth, imageFile);
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @DeleteMapping("/booths/{id}")
    public ResponseResult deleteBoothById(@PathVariable Integer id){
        return boothService.deleteBoothById(id);
    }

}
