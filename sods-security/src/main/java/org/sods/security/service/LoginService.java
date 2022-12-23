package org.sods.security.service;

import org.sods.common.domain.ResponseResult;
import org.sods.security.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
