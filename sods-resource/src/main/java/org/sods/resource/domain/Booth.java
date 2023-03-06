package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("BOOTH")
public class Booth {

    @TableId(value = "booth_id", type = IdType.AUTO)
    private Long id;

    @TableField("title_en")
    private String titleEN;

    @TableField("title_zh")
    private String titleZH;

    @TableField("venue_en")
    private String venueEN;

    @TableField("venue_zh")
    private String venueZH;

    @TableField("description_en")
    private String descriptionEN;

    @TableField("description_zh")
    private String descriptionZH;

    @TableField("image_url")
    private String imageUrl;

    @TableField("vr_image_url")
    private String vrImageUrl;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("delete_flag")
    @TableLogic
    private Integer deleteFlag;

}
