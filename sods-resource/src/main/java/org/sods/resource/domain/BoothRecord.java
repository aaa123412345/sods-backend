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
@TableName("BOOTH_RECORD")
public class BoothRecord {

    @TableField("user_id")
    private Long userId;

    @TableField("booth_id")
    private Long boothId;

    @TableField("visit_end_time")
    private LocalDateTime visitEndTime;

    @TableField("is_got_stamp")
    private Integer isGotStamp;

}
