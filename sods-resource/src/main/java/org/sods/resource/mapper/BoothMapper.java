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
            "b.speech_en, b.speech_zh, b.image_url " +
            "FROM `BOOTH` AS b, `MARKER` AS m " +
            "WHERE m.fk_booth_id = b.booth_id AND m.fk_floorplan_id = #{id}")
    List<Booth> findBoothsByFloorPlanID(Integer id);

}
