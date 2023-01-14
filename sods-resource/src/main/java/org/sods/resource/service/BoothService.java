package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.springframework.web.multipart.MultipartFile;

public interface BoothService {

    ResponseResult createBooth(Booth booth, Double y, Double x, Integer floorPlanId);
    ResponseResult getAllBooths(Integer floorPlanId);
    ResponseResult getBoothById(Integer id);
    ResponseResult updateBoothById(Integer id, Booth newBooth, MultipartFile imageFile);
    ResponseResult deleteBoothById(Integer id);

}
