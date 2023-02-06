package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.SurveyResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/SurveySystem")
public class SurveyResponseController {
    @Autowired
    private SurveyResponseService surveyResponseService;

    @PostMapping("/survey/submit/{active_survey_id}")
    public ResponseResult getSurveyWithPassCode(@PathVariable("active_survey_id")String active_survey_id,
                                                @RequestBody String payload){
        return surveyResponseService.submitSurveyWithData(payload,Long.parseLong(active_survey_id));
    }
}
