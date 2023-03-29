package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ActiveSurvey;
import org.sods.resource.service.ActiveSurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/SurveySystem")
public class ActiveSurveyController {
    @Autowired
    private ActiveSurveyService activeSurveyService;

    @PreAuthorize("@ex.hasAuthority('system:survey:get')")
    @GetMapping("/active_survey")
    public ResponseResult getAllSurvey(){
        return activeSurveyService.getAllActiveSurveyID();
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:get')")
    @GetMapping("/active_survey/{active_survey_id}")
    public ResponseResult getOneActiveSurvey(@PathVariable("active_survey_id")String active_survey_id){

        return activeSurveyService.getDataWithActiveSurveyID(Long.parseLong(active_survey_id));
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:post')")
    @PostMapping("/active_survey")
    public ResponseResult postSurvey(@RequestBody ActiveSurvey activeSurvey){

        return activeSurveyService.createNewActiveSurvey(activeSurvey);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:put')")
    @PutMapping("/active_survey/{active_survey_id}")
    public ResponseResult updateOneSurvey(@PathVariable("active_survey_id")String active_survey_id,@RequestBody ActiveSurvey activeSurvey){
        return activeSurveyService.updateDataWithActiveSurveyID(Long.parseLong(active_survey_id),activeSurvey);
    }

    @PreAuthorize("@ex.hasAuthority('system:survey:delete')")
    @DeleteMapping("/active_survey/{active_survey_id}")
    public ResponseResult removeOneSurvey(@PathVariable("active_survey_id")String active_survey_id){
        return activeSurveyService.deleteDataWithActiveSurveyID(Long.parseLong(active_survey_id));
    }


    @GetMapping("/active_survey_current")
    public ResponseResult getAllSurveyWhichCurrentActive(){
        return activeSurveyService.getAllActiveSurveyIDWhichCurrentActive();
    }

    @GetMapping("/survey/passcode/{pass_code}")
    public ResponseResult getSurveyWithPassCode(@PathVariable("pass_code")String pass_code){
        return activeSurveyService.getSurveyWithPassCode(pass_code);
    }

}
