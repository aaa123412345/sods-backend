package org.sods.websocket.controller;

import org.sods.common.domain.ResponseResult;
import org.sods.websocket.service.RESTVotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/VotingSystem")
public class VoteRestController {
    @Autowired
    private RESTVotingService restVotingService;
    @GetMapping("/passcode/{passcode}")
    public ResponseResult trytoJoin(@PathVariable("passcode")String passcode){
        return restVotingService.checkGroup(passcode);
    }

    @PreAuthorize("@ex.hasAuthority('system:voting:get')")
    @GetMapping("/getExist")
    public ResponseResult getExistGroup(){
        return restVotingService.getExistGroups();
    }
    @PreAuthorize("@ex.hasAuthority('system:voting:post')")
    @PostMapping("/passcode/{passcode}")
    public ResponseResult createVoting(@PathVariable("passcode")String passcode,@RequestBody String surveyID){
        return restVotingService.createGroup(passcode, surveyID);
    }
    @PreAuthorize("@ex.hasAuthority('system:voting:delete')")
    @DeleteMapping ("/passcode/{passcode}")
    public ResponseResult deleteRoom(@PathVariable("passcode")String passcode){
        return restVotingService.removeGroup(passcode);
    }

    @GetMapping("checkSubmit/{passcode}")
    public ResponseResult checkUserSubmit(@PathVariable("passcode") String passcode){
        return restVotingService.checkUserSubmit(passcode);
    }



/*
    @PostMapping ("/submit/{passcode}")
    public ResponseResult userSubmit(@PathVariable("passcode")String passcode,@RequestBody String payload){
        return restVotingService.removeGroup(passcode);
    }*/
}
