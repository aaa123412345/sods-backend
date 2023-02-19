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
@TableName("AR_STATISTIC")
public class ARStatistic {

    @TableId(value = "statistic_id", type = IdType.AUTO)
    private Integer statisticId;

    @TableField("user_id")
    private Long userId;

    @TableField("treasure_id")
    private Integer treasureId;

    @TableField("user_answer")
    private String userAnswer;

    @TableField("score")
    private String score;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

}