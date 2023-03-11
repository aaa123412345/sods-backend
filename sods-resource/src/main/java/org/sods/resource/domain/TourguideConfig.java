package org.sods.resource.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("TOURGUIDE_SYSTEM_CONFIG")
public class TourguideConfig {

    @TableId(value = "config_id", type = IdType.AUTO)
    private Long configId;

    @TableField("theme_color")
    private String themeColor;

    @TableField("openday_date")
    private String opendayDate;

    @TableField("min_stamp_num")
    private Integer minStampNum;

}
