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
@TableName("BOOTH")
public class Booth {

    @TableId(value = "booth_id", type = IdType.AUTO)
    private Integer id;

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

    @TableField("speech_en")
    private String speechEN;

    @TableField("speech_zh")
    private String speechZH;

    @TableField("image_url")
    private String imageUrl;

}
