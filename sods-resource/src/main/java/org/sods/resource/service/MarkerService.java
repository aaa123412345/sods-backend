package org.sods.resource.service;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.Marker;

public interface MarkerService {

    ResponseResult createMarker(Marker marker);
    ResponseResult getAllMarkers();
    ResponseResult getMarkerByFloorPlanIdOrBoothId(Long floorPlanId, Long boothId);
    ResponseResult getMarkerByIds(Double y, Double x, Long floorPlanId);
    ResponseResult assignBoothToMarker(Double y, Double x, Long floorPlanId, Long boothId);
    ResponseResult deleteMarkerByIds(Double y, Double x, Long floorPlanId);

}
