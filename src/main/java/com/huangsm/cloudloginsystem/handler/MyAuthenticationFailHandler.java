package com.huangsm.cloudloginsystem.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangsm.cloudloginsystem.properties.LoginType;
import com.huangsm.cloudloginsystem.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理器
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.handler
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * json 转换工具类
     */
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        //如果是json格式
        if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())){
            //设置状态码
            response.setStatus(500);
            //将 登录失败 信息打包成json格式返回
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(exception));
        }else {
            //如果不是json格式，返回view
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
