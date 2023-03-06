package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sods.resource.domain.Booth;

import java.util.List;

@Mapper
public interface BoothMapper extends BaseMapper<Booth> {

    @Select("SELECT b.booth_id, b.title_en, b.title_zh, " +
            "b.venue_en, b.venue_zh, b.description_en, b.description_zh, " +
            "b.image_url, b.vr_image_url " +
            "FROM `BOOTH` AS b, `MARKER` AS m " +
            "WHERE m.fk_booth_id = b.booth_id AND m.fk_floorplan_id = #{id} AND b.delete_flag = #{deleteFlag}")
    List<Booth> findBoothsByFloorPlanIDAndDeleteFlag(Long id, Integer deleteFlag);

    @Select("SELECT booth_id, title_en, title_zh, " +
            "venue_en, venue_zh, description_en, description_zh, " +
            "image_url, vr_image_url " +
            "FROM `BOOTH` " +
            "WHERE delete_flag = 1")
    List<Booth> findDeletedBooths();

}
