package org.sods.resource.Controller;

import org.sods.common.domain.ResponseResult;
import org.sods.resource.domain.ARAnswer;
import org.sods.resource.service.ARAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/minigame/arAnswers")
public class ARAnswerController {

    @Autowired
    ARAnswerService arAnswerService;

    @PostMapping
    public ResponseResult createARAnswer(@RequestBody ARAnswer arAnswer){
        return arAnswerService.createARAnswer(arAnswer);
    }

    @GetMapping
    public ResponseResult getAllARAnswer(@RequestParam(name = "questionId", required = false) Integer questionId){
        return questionId != null ? arAnswerService.getARAnswerByTreasureId(questionId) : arAnswerService.getAllARAnswer();
    }

    @GetMapping("/{id}")
    public ResponseResult getARAnswerById(@PathVariable Integer id){
        return arAnswerService.getARAnswerById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteARAnswerById(@PathVariable Integer id){
        return arAnswerService.deleteARAnswerById(id);
    }

}
