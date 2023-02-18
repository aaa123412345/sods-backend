package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sods.resource.domain.BoothGame;

@Mapper
public interface BoothGameMapper extends MppBaseMapper<BoothGame> {

    @Select("UPDATE `BOOTH_GAME` SET booth_id = #{newBoothId} AND game_id = #{newGameId} WHERE game_id = #{gameId} AND booth_id = #{boothId}")
    BoothGame updateBoothGameByGameIdAndBoothId(Integer gameId, Integer boothId, Integer newGameId, Integer newBoothId);

    @Select("DELETE FROM `BOOTH_GAME` WHERE game_id = #{gameId} AND booth_id = #{boothId}")
    BoothGame deleteBoothGameByGameIdAndBoothId(Integer gameId, Integer boothId);

}
