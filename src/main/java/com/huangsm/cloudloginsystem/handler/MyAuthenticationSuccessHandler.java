package com.huangsm.cloudloginsystem.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangsm.cloudloginsystem.properties.LoginType;
import com.huangsm.cloudloginsystem.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功处理类
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.handler
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger= LoggerFactory.getLogger(getClass());
    /**
     * json转换工具类
     */
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("登录成功");
        //判断json格式返回还是view格式返回
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            //将authention信息打包成json格式返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            //返回view
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
