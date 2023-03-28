package org.sods.security.service;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;

import java.util.List;

public interface UserService {
    ResponseResult getAllUsers();
    ResponseResult getUser(Long userID);
    ResponseResult addUser(User user, List<Long> roleID);
    ResponseResult editUser(User user, List<Long> roleID);
    ResponseResult removeUser(Long userID);

    ResponseResult banUser(Long userID);

}
