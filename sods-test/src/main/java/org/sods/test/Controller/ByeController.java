package org.sods.test.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByeController {
    @Autowired
    @GetMapping("/bye")
    @PreAuthorize("hasAuthority('system:123:list')")
    public String bye(){
        return  "bye";
    }
}
