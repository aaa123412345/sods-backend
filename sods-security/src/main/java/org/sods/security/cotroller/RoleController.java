package org.sods.security.cotroller;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.Role;
import org.sods.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("security/roles")
    public ResponseResult getRole(){
        return roleService.getAllRoles();
    }

    @GetMapping("security/permissions")
    public ResponseResult getPermission(){
        return roleService.getAllPermissions();
    }

    @GetMapping("security/roleWithpermissions")
    public ResponseResult getRoleWithPermission(){
        return roleService.getAllRoleWithPermissions();
    }

    @PutMapping
    public ResponseResult setPermissionToRole(@RequestParam Long roleID,
                                              @RequestParam List<Long> permissionID){
        return roleService.setPermissionToRole(roleID, permissionID);
    }

    @PostMapping
    public ResponseResult addRole(@RequestParam List<Long> permissionID,@RequestBody Role role){
        return roleService.addRole(role, permissionID);
    }

    @DeleteMapping
    public ResponseResult removeRole(@RequestParam Long roleID){
        return roleService.removeRole(roleID);
    }

    @PutMapping
    public ResponseResult addRoleToUser(@RequestParam Long roleID, @RequestParam Long userID){
        return roleService.addRoleToUser(roleID, userID);
    }

    @PutMapping
    public ResponseResult removeRoleFromUser(@RequestParam Long roleID, @RequestParam Long userID){
        return roleService.removeRoleFromUser(roleID, userID);
    }

}
