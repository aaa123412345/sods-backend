package org.sods.resource.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("survey_activeSurvey")
public class ActiveSurvey {
    @TableId
    private Long activeSurveyId;

    private Long surveyId;

    private String information;

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

    private Boolean allowAnonymous;

    private Boolean allowPublicSearch;

    private String passCode;

    @JSONField(serialize = false)
    public static List<Map> getJsonResultforClient(List<ActiveSurvey> activeSurveyList){
        List<Map> mapList = new ArrayList<>();
        activeSurveyList.forEach((e)->{
            Map<String,Object> stringObjectMap = new HashMap<>();
            stringObjectMap.put("activeSurveyId",e.getActiveSurveyId());
            stringObjectMap.put("information",e.getInformation());
            stringObjectMap.put("surveyId",e.getSurveyId());
            stringObjectMap.put("passCode",e.getPassCode());
            stringObjectMap.put("startTime",e.getStartTime());
            stringObjectMap.put("endTime",e.getEndTime());
            mapList.add(stringObjectMap);
        });
        return mapList;
    }
}
