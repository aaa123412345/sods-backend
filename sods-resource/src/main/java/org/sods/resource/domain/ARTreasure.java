package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("AR_TREASURE")
public class ARTreasure {

    @TableId(value = "treasure_id", type = IdType.AUTO)
    private Integer treasureId;

    @TableField("question_en")
    private String questionEN;

    @TableField("question_zh")
    private String questionZH;
    
    @TableField("answers")
    private String answers;

}
