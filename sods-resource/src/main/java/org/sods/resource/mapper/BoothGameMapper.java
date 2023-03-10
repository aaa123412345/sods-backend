package org.sods.resource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.sods.resource.domain.BoothGame;

@Mapper
public interface BoothGameMapper extends MppBaseMapper<BoothGame> {

    @Select("UPDATE `BOOTH_GAME` SET booth_id = #{newBoothId} AND game_id = #{newGameId} WHERE game_id = #{gameId} AND booth_id = #{boothId}")
    BoothGame updateBoothGameByGameIdAndBoothId(Long gameId, Long boothId, Long newGameId, Long newBoothId);

    @Select("DELETE FROM `BOOTH_GAME` WHERE game_id = #{gameId} AND booth_id = #{boothId}")
    BoothGame deleteBoothGameByGameIdAndBoothId(Long gameId, Long boothId);

    @Select("DELETE FROM `BOOTH_GAME` WHERE game_id = #{gameId}")
    BoothGame deleteBoothGameByGameId(Long gameId);

    @Select("DELETE FROM `BOOTH_GAME` WHERE booth_id = #{boothId}")
    BoothGame deleteBoothGameByBoothId(Long boothId);

}
