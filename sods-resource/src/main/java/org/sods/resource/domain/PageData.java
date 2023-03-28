package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("page_rest_data")
public class PageData {
    @TableId
    private Long pageId;
    private String domain;
    private String path;
    private String language;
    private String pageData;
    private Boolean ableEdit;
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer delFlag;
    @Version
    private Integer version;
}
