package org.sods.security.cotroller;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;
import org.sods.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("security/users")
    @PreAuthorize("@ex.hasAuthority('system:security:get')")
    public ResponseResult getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("security/user/{userID}")
    @PreAuthorize("@ex.hasAuthority('system:security:get')")
    public ResponseResult getUser(@PathVariable Long userID){
        return userService.getUser(userID);
    }

    @PutMapping("security/user")
    @PreAuthorize("@ex.hasAuthority('system:root')")
    public ResponseResult editUser(@RequestBody User user, @RequestParam List<Long> roleID){
        return userService.editUser(user,roleID);
    }

    @PostMapping("security/user")
    @PreAuthorize("@ex.hasAuthority('system:root')")
    public ResponseResult addUser(@RequestBody User user, @RequestParam List<Long> roleID){
        return userService.addUser(user,roleID);
    }

    @DeleteMapping("security/user/{userID}")
    @PreAuthorize("@ex.hasAuthority('system:root')")
    public ResponseResult removeUser(@PathVariable Long userID){
        return userService.removeUser(userID);
    }

}
