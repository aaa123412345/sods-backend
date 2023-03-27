package org.sods.security.service;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;

import java.util.List;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult register(User user);

    ResponseResult setRoleToUser(String userID, List<String> roleNames);
}
