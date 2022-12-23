package org.eie.fyp.sods.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {
    @Autowired
    @GetMapping("/bye")
    public String bye(){
        return  "bye";
    }
}
