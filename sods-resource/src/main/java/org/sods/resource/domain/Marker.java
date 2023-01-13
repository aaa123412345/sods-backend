package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.AutoMap;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("MARKER")
public class Marker {

    //@TableId(type = IdType.NONE)
    //private MarkerPK id;

    @MppMultiId
    private Double y;

    @MppMultiId
    private Double x;

    @MppMultiId
    @TableField("fk_floorplan_id")
    private Integer floorPlanID;

    @TableField("fk_booth_id")
    private Integer boothID;

    //private Booth booth;

}


