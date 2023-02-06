package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("surveyResponse")
public class SurveyResponse {
    @TableId
    private Long activeSurveyId;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Version
    private Integer version;

    private Integer delFlag;
}
