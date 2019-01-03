package com.huangsm.cloudloginsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证类
 *
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.service
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    /**
     * 重写PasswordEncoder  接口中的方法，实例化加密策略
     *
     * @return 返回 BCrypt 加密策略
     */

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Logger logger= LoggerFactory.getLogger(getClass());

    /**
     * 加载用户数据，返回UserDetail实例
     * @param username 用户登录username
     * @return 返回User实体类 做用户校验
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("username:{}",username);
        String password = passwordEncoder.encode("123456");
        //User三个参数(用户名+密码+权限)
        //根据查找到的用户信息判断用户是否被冻结
        logger.info("password:{}",password);
        return new User(username,password,true,true,true
        ,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
