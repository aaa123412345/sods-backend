package org.sods.security.service;

import org.sods.common.domain.ResponseResult;

import java.util.List;

public interface RoleService {
    ResponseResult getAllRoles();
    ResponseResult getAllPermissions();
    ResponseResult getAllRoleWithPermissions();
    ResponseResult setPermissionToRole(Long roleID, List<Long> permissionID);


    ResponseResult addRole(Long roleID, List<Long> permissionID);
    ResponseResult removeRole(Long roleID);
}
