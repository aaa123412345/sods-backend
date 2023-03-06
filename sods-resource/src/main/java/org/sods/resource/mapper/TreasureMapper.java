package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sods.resource.domain.Story;
import org.sods.resource.domain.Treasure;

import java.util.List;

@Mapper
public interface TreasureMapper extends BaseMapper<Treasure> {

    @Select("SELECT * FROM `TREASURE` WHERE delete_flag = 1")
    List<Treasure> findDeletedTreasures();

}
