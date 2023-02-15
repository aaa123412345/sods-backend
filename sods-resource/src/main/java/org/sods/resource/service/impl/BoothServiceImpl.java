package org.sods.resource.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.sods.resource.mapper.BoothMapper;
import org.sods.resource.mapper.MarkerMapper;
import org.sods.resource.service.BoothService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BoothServiceImpl implements BoothService {

    @Autowired
    BoothMapper boothMapper;

    @Autowired
    MarkerMapper markerMapper;

    @Override
    public ResponseResult createBooth(Booth booth, Double y, Double x, Integer floorPlanId) {

        int result = boothMapper.insert(booth);
        Integer newBoothID = booth.getId();

        if(result == 1)
            markerMapper.updateBoothOfMarker(y, x, floorPlanId, newBoothID);

        return new ResponseResult(201, "Booth is created successfully.");

    }

    @Override
    public ResponseResult getAllBooths(Integer floorPlanId) {

        List<Booth> result = floorPlanId != null ? boothMapper.findBoothsByFloorPlanID(floorPlanId) : boothMapper.selectList(null);
        return new ResponseResult(200, "Booth List is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult getBoothById(Integer id) {

        Booth result = boothMapper.selectById(id);
        if(result == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        return new ResponseResult(200, "Booth (id: " + id + ") is retrieved successfully. ", result);

    }

    @Override
    public ResponseResult updateBoothById(Integer id, Booth newBooth, MultipartFile imageFile) {

        Booth booth = boothMapper.selectById(id);

        if(booth == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        if(!imageFile.isEmpty()){
            // upload file to aws
            // get url from cdn
            //booth.setImageUrl(cdn);
        }

        if(booth.getTitleEN() != null)
            booth.setTitleEN(newBooth.getTitleEN());

        if(booth.getTitleZH() != null)
            booth.setTitleZH(newBooth.getTitleZH());

        if(booth.getVenueEN() != null)
            booth.setVenueEN(newBooth.getVenueEN());

        if(booth.getVenueZH() != null)
            booth.setVenueZH(newBooth.getVenueZH());

        if(booth.getDescriptionEN() != null)
            booth.setDescriptionEN(newBooth.getDescriptionEN());

        if(booth.getDescriptionZH() != null)
            booth.setDescriptionZH(newBooth.getDescriptionZH());

        boothMapper.updateById(booth);

        return new ResponseResult(200, "Booth (id: " + id + ") is updated successfully. ");

    }

    @Override
    public ResponseResult deleteBoothById(Integer id) {

        Booth booth = boothMapper.selectById(id);

        if(booth == null)
            return new ResponseResult(404, "Failed: Booth (id: " + id + ") is not found.");

        boothMapper.deleteById(id);

        return new ResponseResult(200, "Booth (id: " + id + ") is deleted successfully. ");

    }
}
