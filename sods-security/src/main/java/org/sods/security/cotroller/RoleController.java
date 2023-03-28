package org.sods.security.cotroller;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.Role;
import org.sods.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("security/roles")
    @PreAuthorize("@ex.hasAuthority('system:security:get')")
    public ResponseResult getRole(){
        return roleService.getAllRoles();
    }

    @GetMapping("security/permissions")
    @PreAuthorize("@ex.hasAuthority('system:security:get')")
    public ResponseResult getPermission(){
        return roleService.getAllPermissions();
    }

    @GetMapping("security/roleWithpermissions")
    @PreAuthorize("@ex.hasAuthority('system:security:get')")
    public ResponseResult getRoleWithPermission(){
        return roleService.getAllRoleWithPermissions();
    }

    @PutMapping("security/role/{roleID}")
    @PreAuthorize("@ex.hasAuthority('system:security:put')")
    public ResponseResult setPermissionToRole(@RequestParam Long roleID,
                                              @RequestParam List<Long> permissionID){
        return roleService.setPermissionToRole(roleID, permissionID);
    }

    @PostMapping("security/role")
    @PreAuthorize("@ex.hasAuthority('system:security:post')")
    public ResponseResult addRole(@RequestParam List<Long> permissionID,@RequestBody Role role){
        return roleService.addRole(role, permissionID);
    }

    @PutMapping("security/role")
    @PreAuthorize("@ex.hasAuthority('system:security:put')")
    public ResponseResult editRole(@RequestParam List<Long> permissionID,@RequestBody Role role){
        return roleService.editRole(role, permissionID);
    }

    @DeleteMapping("security/role/{roleID}")
    @PreAuthorize("@ex.hasAuthority('system:security:delete')")
    public ResponseResult removeRole(@PathVariable Long roleID){
        return roleService.removeRole(roleID);
    }



}
