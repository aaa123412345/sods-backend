package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("TREASURE_STATISTIC")
public class TreasureStatistic {

    @TableId(value = "statistic_id", type = IdType.AUTO)
    private Long statisticId;

    @TableField("user_id")
    private Long userId;

    @TableField("treasure_id")
    private Long treasureId;

    @TableField("user_answer")
    private String userAnswer;

    @TableField("score")
    private String score;

    @TableField("start_time")
    private String startTime;

    @TableField("end_time")
    private String endTime;

}
