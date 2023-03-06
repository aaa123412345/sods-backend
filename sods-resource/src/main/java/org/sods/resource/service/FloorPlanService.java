package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.FloorPlan;
import org.springframework.web.multipart.MultipartFile;

public interface FloorPlanService {

    ResponseResult createFloorPlan(FloorPlan floorPlan);
    //ResponseResult createFloorPlan(FloorPlan floorPlan, MultipartFile imageFile);
    ResponseResult getAllFloorPlan();
    ResponseResult getFloorPlanById(Long id);
    ResponseResult updateFloorPlanById(Long id, FloorPlan newFloorPlan);
    //ResponseResult updateFloorPlanById(Integer id, FloorPlan newFloorPlan, MultipartFile imageFile);
    ResponseResult deleteFloorPlanById(Long id);

}
