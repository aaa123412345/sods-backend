package org.sods.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.Menu;
import org.sods.security.domain.MenuRole;
import org.sods.security.domain.Role;
import org.sods.security.domain.UserRole;
import org.sods.security.mapper.MenuMapper;
import org.sods.security.mapper.MenuRoleMapper;
import org.sods.security.mapper.RoleMapper;
import org.sods.security.mapper.UserRoleMapper;
import org.sods.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public ResponseResult getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        return new ResponseResult<>(HttpStatus.OK.value(), "Get all roles successfully", roles);

    }

    @Override
    public ResponseResult getAllPermissions() {
        List<Menu> menus = menuMapper.selectList(null);
        return new ResponseResult<>(HttpStatus.OK.value(), "Get all permissions successfully", menus);
    }

    @Override
    public ResponseResult getAllRoleWithPermissions() {
        List<Role> roles = roleMapper.selectList(null);
        List<Menu> menus = menuMapper.selectList(null);
        List<MenuRole> menuRoles = menuRoleMapper.selectList(null);

        List<Map> finalResult = new ArrayList<>();

        List<String> rolePermissionsName = new ArrayList<>();
        for (Role role : roles) {
            Map<String,Object> roleResult = new HashMap<>();
            roleResult.put("roles",roles);
            for (MenuRole menuRole : menuRoles) {
                if (role.getId().equals(menuRole.getRoleId())) {
                    for (Menu menu : menus) {
                        if (menu.getId().equals(menuRole.getMenuId())) {
                            rolePermissionsName.add(menu.getPerms());
                        }
                    }
                }
            }
            roleResult.put("permission",rolePermissionsName);
            finalResult.add(roleResult);
        }


        return new ResponseResult<>(HttpStatus.OK.value(), "Get all role with permissions successfully", finalResult);
    }

    @Override
    @Transactional
    public ResponseResult setPermissionToRole(Long roleID, List<Long> permissionID) {
        try{
            //Delete all permissions of this role
            QueryWrapper<MenuRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleID);
            menuRoleMapper.delete(queryWrapper);
            //Add new permissions to this role
            for (Long permission : permissionID) {
                MenuRole menuRole = new MenuRole();
                menuRole.setMenuId(permission);
                menuRole.setRoleId(roleID);
                menuRoleMapper.insert(menuRole);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Set permission to role failed");
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "Set permission to role successfully");
    }

    @Override
    @Transactional
    public ResponseResult addRole(Role role, List<Long> permissionID) {
        try {
            //Add role
            roleMapper.insert(role);
            //Add permissions to this role
            for (Long permission : permissionID) {
                MenuRole menuRole = new MenuRole();
                menuRole.setMenuId(permission);
                menuRole.setRoleId(role.getId());
                menuRoleMapper.insert(menuRole);
            }


        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Add role failed");
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "Add role successfully");
    }

    @Override
    @Transactional
    public ResponseResult removeRole(Long roleID) {
        try{
            //Delete role
            roleMapper.deleteById(roleID);
            //Delete all permissions of this role
            QueryWrapper<MenuRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",roleID);
            menuRoleMapper.delete(queryWrapper);
            //Delete all users of this role
            QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("role_id",roleID);
            userRoleMapper.delete(queryWrapper1);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Remove role failed");
        }
        return new ResponseResult<>(HttpStatus.OK.value(), "Remove role successfully");
    }

    @Override
    public ResponseResult addRoleToUser(Long roleID, Long userID) {
        //Add role to user
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleID);
        userRole.setUserId(userID);
        userRoleMapper.insert(userRole);

        return new ResponseResult<>(HttpStatus.OK.value(), "Add role to user successfully");
    }

    @Override
    public ResponseResult removeRoleFromUser(Long roleID, Long userID) {
        //Remove role from user
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleID);
        queryWrapper.eq("user_id",userID);
        userRoleMapper.delete(queryWrapper);

        return new ResponseResult<>(HttpStatus.OK.value(), "Remove role from user successfully");
    }
}