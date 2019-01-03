package com.huangsm.cloudloginsystem.config;

import com.huangsm.cloudloginsystem.handler.MyAuthenticationFailHandler;
import com.huangsm.cloudloginsystem.handler.MyAuthenticationSuccessHandler;
import com.huangsm.cloudloginsystem.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Security配置类
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.config
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 注入security属性类配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 重写PasswordEncoder接口中的方法，实例化加密策略
     * @return 返回BCrypt加密策略
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义的登录成功处理类
     */
    @Autowired
    private MyAuthenticationSuccessHandler mySuccessHandler;

    /**
     * 注入自定义的登录失败处理类
     */
    @Autowired
    private MyAuthenticationFailHandler myFailHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String redirectUrl = securityProperties.getBrowser().getLoginPage();

        //表单登录方式
        http
                .formLogin()
                .loginPage("/auth/require")
                .loginProcessingUrl("/auth/form")
                .successHandler(mySuccessHandler)
                .failureHandler(myFailHandler)
                .and()
                //请求授权
                .authorizeRequests()
                //不需要权限验证的url
                .antMatchers("/auth/require",redirectUrl).permitAll()
                //任何请求
                .anyRequest()
                //需要身份验证
                .authenticated()
                .and()
                //关闭跨站请求防护
                .csrf().disable();
    }
}
