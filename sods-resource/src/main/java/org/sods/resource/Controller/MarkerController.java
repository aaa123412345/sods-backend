package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Marker;
import org.sods.resource.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/TourGuide/markers")
public class MarkerController {

    @Autowired
    MarkerService markerService;

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @PostMapping
    public ResponseResult createMarker(@RequestBody Marker marker){
        return markerService.createMarker(marker);
    }

    @GetMapping
    public ResponseResult getAllMarkers(
            @RequestParam(name = "floorplanId", required = false) Long floorPlanID,
            @RequestParam(name = "boothId", required = false) Long boothID
    ){
        return markerService.getMarkerByFloorPlanIdOrBoothId(floorPlanID, boothID);
    }


    @GetMapping("/{y}/{x}/{floorPlanID}")
    public ResponseResult getMarkerById(@PathVariable Double y, @PathVariable Double x, @PathVariable Long floorPlanID){
        return markerService.getMarkerByIds(y, x, floorPlanID);
    }

    @PutMapping("/{y}/{x}/{floorPlanID}")
    public ResponseResult getMarkerById(@PathVariable Double y, @PathVariable Double x, @PathVariable Long floorPlanID, @RequestBody Marker marker){
        return markerService.assignBoothToMarker(y, x, floorPlanID, marker.getBoothID());
    }

    @PreAuthorize("@ex.hasAuthority('system:tourguide:root')")
    @DeleteMapping("/{y}/{x}/{floorPlanID}")
    public ResponseResult deleteMarkerById(@PathVariable Double y, @PathVariable Double x, @PathVariable Long floorPlanID){
        return markerService.deleteMarkerByIds(y, x, floorPlanID);
    }

}
