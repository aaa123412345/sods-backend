package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.service.ActiveSurveyService;
import org.sods.resource.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/SurveySystem")
public class ActiveSurveyController {
    @Autowired
    private ActiveSurveyService activeSurveyService;

    @PreAuthorize("@ex.hasAuthority('system:survey:active:read')")
    @GetMapping("/active_survey")
    public ResponseResult getAllSurvey(){
        return activeSurveyService.getAllActiveSurveyID();
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:active:read')")
    @GetMapping("/active_survey/{active_survey_id}")
    public ResponseResult getOneActiveSurvey(@PathVariable("active_survey_id")String active_survey_id){

        return activeSurveyService.getDataWithActiveSurveyID(Long.parseLong(active_survey_id));
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:active:create')")
    @PostMapping("/active_survey")
    public ResponseResult postSurvey(@RequestBody ActiveSurvey activeSurvey){

        return activeSurveyService.createNewActiveSurvey(activeSurvey);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:active:update')")
    @PutMapping("/active_survey/{active_survey_id}")
    public ResponseResult updateOneSurvey(@PathVariable("active_survey_id")String active_survey_id,@RequestBody ActiveSurvey activeSurvey){
        return activeSurveyService.updateDataWithActiveSurveyID(Long.parseLong(active_survey_id),activeSurvey);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:active:delete')")
    @DeleteMapping("/active_survey/{active_survey_id}")
    public ResponseResult removeOneSurvey(@PathVariable("active_survey_id")String active_survey_id){
        return activeSurveyService.deleteDataWithActiveSurveyID(Long.parseLong(active_survey_id));
    }
}
