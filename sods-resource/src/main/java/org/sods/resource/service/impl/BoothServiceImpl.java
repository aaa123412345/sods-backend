package org.sods.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.sods.resource.domain.BoothGame;
import org.sods.resource.domain.Marker;
import org.sods.resource.mapper.BoothGameMapper;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.sods.resource.mapper.TourguideConfigMapper;
import org.sods.resource.service.BoothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoothServiceImpl implements BoothService {

    @Autowired
    TourguideConfigMapper tourguideConfigMapper;

    @Autowired
    BoothMapper boothMapper;

    @Autowired
    BoothGameMapper boothGameMapper;

    @Autowired
    MarkerMapper markerMapper;

    @Override
    public ResponseResult createBooth(Booth booth){
        boothMapper.insert(booth);
        return new ResponseResult(201, "Booth is created successfully.");
    }

    @Override
    public ResponseResult createBoothWithMaker(Booth booth, Double y, Double x, Long floorPlanId) {

        int result = boothMapper.insert(booth);
        Long newBoothID = booth.getId();

        if(result == 1)
            markerMapper.updateBoothOfMarker(y, x, floorPlanId, newBoothID);

        return new ResponseResult(201, "Booth is created successfully.");

    }

    @Override
    public ResponseResult getAllBooths(Long floorPlanId, Integer deleteFlag) {

        Integer delFlag = deleteFlag == null ? 0 : deleteFlag;

        List<Booth> result;

        if(floorPlanId != null)
            result = boothMapper.findBoothsByFloorPlanIDAndDeleteFlag(floorPlanId, delFlag);
        else if(delFlag == 0)
            result = boothMapper.selectList(null);
        else
            result = boothMapper.findDeletedBooths();

        return new ResponseResult(200, "Booth List is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult getBoothById(Long id) {

        Booth result = boothMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        return new ResponseResult(200, "Booth (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateBoothById(Long id, Booth newBooth) {

        Booth booth = boothMapper.selectById(id);

        if (booth == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        String newImageUrl = newBooth.getImageUrl() == null ? booth.getImageUrl() : newBooth.getImageUrl();
        String newVrImageUrl = newBooth.getVrImageUrl() == null ? booth.getVrImageUrl() : newBooth.getVrImageUrl();

        booth.setTitleEN(newBooth.getTitleEN());
        booth.setTitleZH(newBooth.getTitleZH());
        booth.setVenueEN(newBooth.getVenueEN());
        booth.setVenueZH(newBooth.getVenueZH());
        booth.setDescriptionEN(newBooth.getDescriptionEN());
        booth.setDescriptionZH(newBooth.getDescriptionZH());
        booth.setImageUrl(newImageUrl);
        booth.setVrImageUrl(newVrImageUrl);

        boothMapper.updateById(booth);

        return new ResponseResult(200, "Booth (id: " + id + ") is updated successfully. ");

    }

    @Override
    public ResponseResult deleteBoothById(Long id) {

        Booth booth = boothMapper.selectById(id);

        if(booth == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        int result = boothMapper.deleteById(id);

        if(result == 1){

            QueryWrapper markerQuery = new QueryWrapper<>();
            markerQuery.eq("fk_booth_id", id);
            List<Marker> markers = markerMapper.selectList(markerQuery);
            if(!markers.isEmpty()){
                Marker marker = markers.get(0);
                marker.setBoothID(null);
                markerMapper.insert(marker);
            }
            QueryWrapper boothGameQuery = new QueryWrapper<>();
            boothGameQuery.eq("booth_id", id);
            List<BoothGame> boothGames = boothGameMapper.selectList(boothGameQuery);
            if(!boothGames.isEmpty()) {
                boothGameMapper.deleteBoothGameByBoothId(id);
            }

        }

        return new ResponseResult(200, "Booth (id: " + id + ") is deleted successfully. ");

    }
}
