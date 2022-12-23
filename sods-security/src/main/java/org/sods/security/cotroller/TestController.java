package org.sods.security.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    @GetMapping("/hi")
    public String hi(){
        return  "hi";
    }
}
