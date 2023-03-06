package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Booth;
import org.springframework.web.multipart.MultipartFile;

public interface BoothService {

    ResponseResult createBooth(Booth booth);

    ResponseResult createBoothWithMaker(Booth booth, Double y, Double x, Long floorPlanId);
    ResponseResult getAllBooths(Long floorPlanId, Integer deleteFlag);
    ResponseResult getBoothById(Long id);
    ResponseResult updateBoothById(Long id, Booth newBooth);
    //ResponseResult updateBoothById(Integer id, Booth newBooth, MultipartFile imageFile);
    ResponseResult deleteBoothById(Long id);

}
