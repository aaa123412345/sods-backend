package org.sods.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;
import org.sods.security.domain.UserRole;
import org.sods.security.mapper.UserMapper;
import org.sods.security.mapper.UserRoleMapper;
import org.sods.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public ResponseResult getAllUsers() {
        List<User> users = userMapper.selectList(null);
        return new ResponseResult<>(HttpStatus.OK.value(), "Get all users successfully", users);
    }

    @Override
    public ResponseResult getUser(Long userID) {
        User user = userMapper.selectById(userID);
        return new ResponseResult<>(HttpStatus.OK.value(), "Get user successfully", user);

    }

    @Override
    public ResponseResult addUser(User user, List<Long> roleID) {
        try{
            userMapper.insert(user);
            for (Long role : roleID) {
                userRoleMapper.insert(new UserRole(user.getId(), role));
            }
            return new ResponseResult<>(HttpStatus.OK.value(), "Add user successfully", user);
        }catch (Exception e) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Add user failed", e.getMessage());
        }
    }

    @Override
    public ResponseResult editUser(User user, List<Long> roleID) {
        try{
            userMapper.updateById(user);
            userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
            for (Long role : roleID) {
                userRoleMapper.insert(new UserRole(user.getId(), role));
            }
            return new ResponseResult<>(HttpStatus.OK.value(), "Edit user successfully", user);
        }catch (Exception e) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Edit user failed", e.getMessage());
        }
    }

    @Override
    public ResponseResult removeUser(Long userID) {
           try{
                userMapper.deleteById(userID);
                userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userID));
                return new ResponseResult<>(HttpStatus.OK.value(), "Remove user successfully", userID);
            }catch (Exception e) {
                return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Remove user failed", e.getMessage());
            }
    }

    @Override
    public ResponseResult banUser(Long userID) {
        try{
            User user = userMapper.selectById(userID);
            user.setStatus("1");
            userMapper.updateById(user);
            return new ResponseResult<>(HttpStatus.OK.value(), "Ban user successfully", user);
        }catch (Exception e) {
            return new ResponseResult<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Ban user failed", e.getMessage());
        }
    }


}
