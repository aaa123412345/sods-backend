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
@TableName("STORY")
public class Story {

    @TableId(value = "story_id", type = IdType.AUTO)
    private Integer id;

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

}

