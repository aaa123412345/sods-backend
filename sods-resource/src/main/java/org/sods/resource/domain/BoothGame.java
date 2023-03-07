package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("BOOTH_GAME")
public class BoothGame {

    @MppMultiId
    @TableField("booth_id")
    private Long boothId;

    @MppMultiId
    @TableField("game_id")
    private Long gameId;

}
