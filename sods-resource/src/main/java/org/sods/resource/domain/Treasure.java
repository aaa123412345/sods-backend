package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("TREASURE")
public class Treasure {

    @TableId(value = "treasure_id", type = IdType.AUTO)
    private Long treasureId;

    @TableField("question_en")
    private String questionEN;

    @TableField("question_zh")
    private String questionZH;
    
    @TableField("answers")
    private String answers;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;


}
