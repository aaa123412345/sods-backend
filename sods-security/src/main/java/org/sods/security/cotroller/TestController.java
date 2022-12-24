package org.sods.security.cotroller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("@ex.hasAuthority('system:root')")
    @GetMapping("/hi")
    public String hi(){
        return  "private hi";
    }

    @GetMapping("/public/hi")
    public String publicHi(){
        return  "public hi";
    }
}
