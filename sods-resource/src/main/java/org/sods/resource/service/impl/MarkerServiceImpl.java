package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Marker;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.sods.resource.mapper.TourguideConfigMapper;
import org.sods.resource.service.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MarkerServiceImpl implements MarkerService {

    @Autowired
    MarkerMapper markerMapper;

    @Autowired
    BoothMapper boothMapper;

    @Override
    public ResponseResult createMarker(Marker marker) {

        markerMapper.insert(marker);
        return new ResponseResult(201, "Marker is added successfully.");

    }

    @Override
    public ResponseResult getAllMarkers() {

        List<Marker> result = markerMapper.selectList(null);
        return new ResponseResult(200, "Marker List is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult getMarkerByFloorPlanIdOrBoothId(Long floorPlanId, Long boothId) {

        QueryWrapper<Marker> query = new QueryWrapper<>();
        if(floorPlanId != null)
            query.eq("fk_floorplan_id", floorPlanId);
        if(boothId != null)
            query.eq("fk_booth_id", boothId);

        List<Marker> result = markerMapper.selectList(query);

        return new ResponseResult(200, "Marker List is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult getMarkerByIds(Double y, Double x, Long floorPlanId) {

        Marker result = markerMapper.findMarkerByCID(y, x, floorPlanId);

        if(result == null)
            return new ResponseResult(404, "Failed: Marker is not found. ");

        return new ResponseResult(200, "Marker is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult assignBoothToMarker(Double y, Double x, Long floorPlanId, Long boothId) {

        Marker result = markerMapper.findMarkerByCID(y, x, floorPlanId);

        if(result == null)
            return new ResponseResult(404, "Failed: Marker is not found. ");
        markerMapper.updateBoothOfMarker(y, x, floorPlanId, boothId);

        return new ResponseResult(200, "Marker is assigned to booth successfully. ", result);
    }

    @Override
    public ResponseResult deleteMarkerByIds(Double y, Double x, Long floorPlanId) {

        Marker marker = markerMapper.findMarkerByCID(y, x, floorPlanId);
        if(marker == null)
            return new ResponseResult(404, "Failed: Marker is not found. ");

        Marker result = markerMapper.deleteMarkerByCID(y, x, floorPlanId);
        if(result != null && result.getBoothID() != null)
            boothMapper.deleteById(result.getBoothID());

        return new ResponseResult(200, "Marker is deleted successfully. ");

    }
}
