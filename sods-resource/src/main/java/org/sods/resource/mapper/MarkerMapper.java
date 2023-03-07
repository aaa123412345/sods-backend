package org.sods.resource.mapper;

import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.*;
import org.sods.resource.domain.Marker;

@Mapper
public interface MarkerMapper extends MppBaseMapper<Marker> {


    @Select("SELECT * FROM `MARKER` WHERE x = #{x} AND y = #{y} AND fk_floorplan_id = #{floorPlanID}")
    Marker findMarkerByCID(Double y, Double x, Long floorPlanID);

    @Select("SELECT * FROM `MARKER` WHERE fk_booth_id = #{id}")
    Marker findMarkerByBoothID(Long id);

    @Select("UPDATE `MARKER` SET fk_booth_id = #{boothID} WHERE x = #{x} AND y = #{y} AND fk_floorplan_id = #{floorPlanID}")
    Marker updateBoothOfMarker(Double y, Double x, Long floorPlanID, Long boothID);

    @Select("DELETE FROM `MARKER` WHERE x = #{x} AND y = #{y} AND fk_floorplan_id = #{floorPlanID}")
    Marker deleteMarkerByCID(Double y, Double x, Long floorPlanID);

}
