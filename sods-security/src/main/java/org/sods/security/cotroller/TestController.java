package org.sods.security.cotroller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("@ex.hasAuthority('system:root')")
    @GetMapping("/private/hi")
    public String hi(){
        return  "permission system root";
    }

    @PreAuthorize("@ex.hasAuthority('system:abc')")
    @GetMapping("/permissionTest")
    public String t1(){
        return  "permission abc";
    }


    @GetMapping("test/public/hi")
    public String publicHi(){
        return  "public hi";
    }
}
