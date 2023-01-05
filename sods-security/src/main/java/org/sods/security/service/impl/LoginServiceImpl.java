package org.sods.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.sods.common.domain.ResponseResult;
import org.sods.common.utils.JwtUtil;
import org.sods.common.utils.RedisCache;
import org.sods.security.domain.LoginUser;
import org.sods.security.domain.User;
import org.sods.security.domain.UserRole;
import org.sods.security.mapper.MenuMapper;
import org.sods.security.mapper.UserMapper;
import org.sods.security.mapper.UserRoleMapper;
import org.sods.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public ResponseResult login(User user) {

        //User Validation
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //User info is wrong
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("Login Failed");
        }

        //User Info is correct
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);

        //save complete info of user to redis
        redisCache.setCacheObject("login:"+userid,loginUser);

        //Ready the response
        List<String> list = menuMapper.selectPermsByUserId(Long.parseLong(userid));
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        map.put("rolePermission",list);
        map.put("userType",loginUser.getUser().getUserType());

        return new ResponseResult(200,"login success",map);

    }

    @Override
    public ResponseResult logout() {
        //Get user info
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken)SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        //remove the user info key in redis
        redisCache.deleteObject("login:"+id);
        return new ResponseResult(200,"Logout Success");
    }



    @Override
    public ResponseResult register(User user) {
        //check user syntax
        if(!StringUtils.hasText(user.getUserName())||!StringUtils.hasText(user.getUserName())){
            return new ResponseResult(400,"Registration Failed, Not enough parameter");
        }

        //Check if the username exist
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,user.getUserName());
        User test = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(test)){
            //Encode the password for saving in sql
            String encode1 = passwordEncoder.encode(user.getPassword());
            user.setPassword(encode1);
            //Get the current data
            LocalDateTime currentDate = LocalDateTime.now();
            user.setCreateTime(currentDate);
            user.setUpdateTime(currentDate);
            user.setCreateBy(0L);
            user.setUpdateBy(0L);
            //Insert database
            userMapper.insert(user);
            //Role id = 2 => Visitor (Default)
            userRoleMapper.insert(new UserRole(user.getId(), 2L));
            return new ResponseResult(200,"Registration Success");
        }else{
            return new ResponseResult(400,"Registration Failed, The user name exist.");
        }


    }
}
