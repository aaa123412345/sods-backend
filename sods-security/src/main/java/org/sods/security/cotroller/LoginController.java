package org.sods.security.cotroller;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;
import org.sods.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);
    }


    @RequestMapping("/user/register")
    public ResponseResult register(@RequestBody User user){
        return loginService.register(user);
    }

    @RequestMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
