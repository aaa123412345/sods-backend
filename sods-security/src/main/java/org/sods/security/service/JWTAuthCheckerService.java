package org.sods.security.service;

import org.sods.security.domain.LoginUser;

public interface JWTAuthCheckerService {
    LoginUser checkWithJWT(String token);
}
