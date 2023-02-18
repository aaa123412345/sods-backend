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
@TableName("AR_ANSWER")
public class ARAnswer {

    @TableId(value = "answer_id", type = IdType.AUTO)
    private Integer answerId;

    @TableField("answer")
    private String answer;

    @TableField
    private Integer treasureId;

}
