package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Marker;

public interface MarkerService {

    ResponseResult createMarker(Marker marker);
    ResponseResult getAllMarkers();
    ResponseResult getMarkerByFloorPlanIdOrBoothId(Integer floorPlanId, Integer boothId);
    ResponseResult getMarkerByIds(Double y, Double x, Integer floorPlanId);
    ResponseResult deleteMarkerByIds(Double y, Double x, Integer floorPlanId);

}
