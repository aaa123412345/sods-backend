package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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

    private String createUserId;

    private String modifyUserId;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

    private Integer delflag;

    @Version
    private Integer version;

    @TableField(typeHandler = FastjsonTypeHandler.class)
    private String pageData;
}
