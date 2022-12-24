package org.sods.application;

import org.sods.resource.domain.PageData;
import org.sods.resource.mapper.PageDataMapper;
import org.sods.resource.mapper.PageRouterMapper;
import org.sods.security.domain.User;
import org.sods.security.mapper.MenuMapper;
import org.sods.security.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void TestBCryptPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("1234");
        String encode2 = passwordEncoder.encode("1234");
        System.out.println(encode1);
        System.out.println(encode2);
    }

    @Autowired
    private PageDataMapper pageDataMapper;
    @Test
    public void tesPageMapper(){
        PageData s = pageDataMapper.selectById("1");
        System.out.println(s);
    }

    @Autowired
    private MenuMapper menuMapper;
    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Autowired
    private PageRouterMapper pageRouterMapper;
    @Test
    public void testSelectPageDataByURLAndLan(){
        String js = pageRouterMapper.selectPageDataByURLAndLan("public/about","eng");
        System.out.println(js);
    }

    @Test
    public  void testSelectPermsByUserId(){
        List<String> list = menuMapper.selectPermsByUserId(1606651715013095425L);
        System.out.println(list);
    }

    @Test
    public  void aa(){

        System.out.println(1);
    }

}
