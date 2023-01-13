package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("designedSurvey")
public class Survey {
    @TableId
    private Long surveyId;

    private Long createUserId;

    private Long updateUserId;

    private Integer delflag;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private String surveyFormat;
}
