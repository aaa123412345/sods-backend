package org.sods.security.service.impl;

import org.sods.common.domain.ResponseResult;
import org.sods.security.service.RoleService;

import java.util.List;

public class RoleServiceImpl implements RoleService {

    @Override
    public ResponseResult getAllRoles() {
        return null;
    }

    @Override
    public ResponseResult getAllPermissions() {
        return null;
    }

    @Override
    public ResponseResult getAllRoleWithPermissions() {
        return null;
    }

    @Override
    public ResponseResult setPermissionToRole(Long roleID, List<Long> permissionID) {
        return null;
    }

    @Override
    public ResponseResult addRole(Long roleID, List<Long> permissionID) {
        return null;
    }

    @Override
    public ResponseResult removeRole(Long roleID) {
        return null;
    }
}
