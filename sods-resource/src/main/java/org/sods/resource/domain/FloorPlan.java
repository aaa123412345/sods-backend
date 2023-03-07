package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("FLOORPLAN")
public class FloorPlan {

    @TableId(value = "floorplan_id", type = IdType.AUTO)
    private Long id;

    @TableField("region_en")
    private String regionEN;

    @TableField("region_zh")
    private String regionZH;

    @TableField("image_url")
    private String imageUrl;

}
