package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.FloorPlan;
import org.springframework.web.multipart.MultipartFile;

public interface FloorPlanService {

    ResponseResult createFloorPlan(FloorPlan floorPlan);
    //ResponseResult createFloorPlan(FloorPlan floorPlan, MultipartFile imageFile);
    ResponseResult getAllFloorPlan();
    ResponseResult getFloorPlanById(Integer id);
    ResponseResult updateFloorPlanById(Integer id, FloorPlan newFloorPlan);
    //ResponseResult updateFloorPlanById(Integer id, FloorPlan newFloorPlan, MultipartFile imageFile);
    ResponseResult deleteFloorPlanById(Integer id);

}
