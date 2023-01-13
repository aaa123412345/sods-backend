package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/SurveySystem")
public class SurveyController {
    @Autowired
    SurveyService surveyService;

    @PreAuthorize("@ex.hasAuthority('system:survey:editor:read')")
    @GetMapping("/survey")
    public ResponseResult getAllSurvey(){
        return surveyService.listAll();
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:editor:read')")
    @GetMapping("/survey/{survey_id}")
    public ResponseResult getOneSurvey(@PathVariable("survey_id")String survey_id){
        return surveyService.get(Long.parseLong(survey_id));
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:editor:create')")
    @PostMapping("/survey")
    public ResponseResult postSurvey(@RequestBody String payload){
        System.out.println(payload);
        return surveyService.post(payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:editor:update')")
    @PutMapping("/survey/{survey_id}")
    public ResponseResult updateOneSurvey(@PathVariable("survey_id")String survey_id,@RequestBody String payload){
        return surveyService.put(Long.parseLong(survey_id),payload);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:editor:delete')")
    @DeleteMapping("/survey/{survey_id}")
    public ResponseResult removeOneSurvey(@PathVariable("survey_id")String survey_id){
        return surveyService.delete(Long.parseLong(survey_id));
    }
}
