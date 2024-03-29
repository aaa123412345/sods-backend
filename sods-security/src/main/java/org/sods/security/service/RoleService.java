package org.sods.security.service;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.Role;

import java.util.List;

public interface RoleService {
    ResponseResult getAllRoles();

    ResponseResult getRole(Long roleID);
    ResponseResult getAllPermissions();
    ResponseResult getAllRoleWithPermissions();
    ResponseResult setPermissionToRole(Long roleID, List<Long> permissionID);


    ResponseResult addRole(Role role, List<Long> permissionID);
    ResponseResult editRole(Role role, List<Long> permissionID);
    ResponseResult removeRole(Long roleID);



}
