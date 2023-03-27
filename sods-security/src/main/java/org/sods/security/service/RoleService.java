package org.sods.security.service;

import org.sods.common.domain.ResponseResult;

import java.util.List;

public interface RoleService {
    ResponseResult getRoles();
    ResponseResult getPermissions();
    ResponseResult setPermissionToRole(String roleID, List<String> permissionNames);
}
