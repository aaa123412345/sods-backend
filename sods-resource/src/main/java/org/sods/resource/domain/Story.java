package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("STORY")
public class Story {

    @TableId(value = "story_id", type = IdType.AUTO)
    private Long id;

    @TableField("title_en")
    private String titleEN;

    @TableField("title_zh")
    private String titleZH;

    @TableField("content_en")
    private String contentEN;

    @TableField("content_zh")
    private String contentZH;

    @TableField("image_url")
    private String imageUrl;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;


}

