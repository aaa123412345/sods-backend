package org.sods.resource.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("booking_activity_information")
public class BookingActivityInformation {

    @TableId
    private Long bookingActivityId;

    private String information;

    private String location;

    private Integer maxQuote;

    private Integer currentNum;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Version
    private Integer version;

    private Integer delFlag;

    @JSONField(serialize = false)
    public static List<Map> getJsonResultforClient(List<BookingActivityInformation> bookingActivityInformationList){
        List<Map> mapList = new ArrayList<>();
        bookingActivityInformationList.forEach((e)->{
            Map<String,Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("bookingActivityId",e.getBookingActivityId());
            stringObjectMap.put("information",e.getInformation());
            stringObjectMap.put("location",e.getLocation());
            stringObjectMap.put("maxQuote",e.getMaxQuote());
            stringObjectMap.put("currentNum",e.getCurrentNum());
            stringObjectMap.put("startTime",e.getStartTime());
            stringObjectMap.put("endTime",e.getEndTime());
            mapList.add(stringObjectMap);
        });
        return mapList;
    }
}
