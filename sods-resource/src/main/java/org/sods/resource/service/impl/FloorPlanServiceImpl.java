package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.FloorPlan;
import org.sods.resource.domain.Marker;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.FloorPlanMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.sods.resource.service.FloorPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FloorPlanServiceImpl implements FloorPlanService {

    @Autowired
    FloorPlanMapper floorPlanMapper;

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    BoothMapper boothMapper;

    @Override
    public ResponseResult createFloorPlan(@RequestBody FloorPlan floorPlan) {
        floorPlanMapper.insert(floorPlan);
        return new ResponseResult(201,"New floor plan is created successfully.");
    }

    @Override
    public ResponseResult getAllFloorPlan() {
        List<FloorPlan> result = floorPlanMapper.selectList(null);
        return new ResponseResult(200, "Floor Plan List is retrieved successfully. ", result);
    }

    @Override
    public ResponseResult getFloorPlanById(Long id) {

        FloorPlan result = floorPlanMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Floor Plan (id: " + id + ") is not found.");

        return new ResponseResult(200, "Floor Plan (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateFloorPlanById(Long id, FloorPlan newFloorPlan) {

        FloorPlan floorPlan = floorPlanMapper.selectById(id);
        if(floorPlan == null)
            return new ResponseResult(404, "Failed: Floor Plan (id: " + id + ") is not found. ");

        String newImageUrl = newFloorPlan.getImageUrl() == null ? floorPlan.getImageUrl() : newFloorPlan.getImageUrl();

        floorPlan.setRegionEN(newFloorPlan.getRegionEN());
        floorPlan.setRegionZH(newFloorPlan.getRegionZH());
        floorPlan.setImageUrl(newImageUrl);

        floorPlanMapper.updateById(floorPlan);
        return new ResponseResult(200, "Floor Plan (id: " + id + ") is updated successfully.");

    }

    @Override
    public ResponseResult deleteFloorPlanById(Long id) {

        FloorPlan floorPlan = floorPlanMapper.selectById(id);
        if(floorPlan == null)
            return new ResponseResult(404, "Failed: Floor Plan (id: " + id + ") is not found. ");

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

        floorPlanMapper.deleteById(id);
        return new ResponseResult(200,"Floor Plan (id: " + id + ") with related markers and booths are deleted successfully. ");

    }
}
