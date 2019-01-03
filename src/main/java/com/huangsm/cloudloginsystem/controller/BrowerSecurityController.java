package com.huangsm.cloudloginsystem.controller;

import com.huangsm.cloudloginsystem.properties.SecurityProperties;
import com.huangsm.cloudloginsystem.utils.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 判断json/html请求 返回不同结果
 *
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.controller
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */

/**
 * 响应状态码 UNAUTHORIZED(401, "Unauthorized")
 */
@RestController
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class BrowerSecurityController {
    private Logger logger= LoggerFactory.getLogger(getClass());
    /**
     * 重定向 策略
     */
    private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();

    /**
     * 把当前的请求缓存到session里去
     */
    private RequestCache requestCache=new HttpSessionRequestCache();

    /**
     * 注入security属性类配置
     */
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 当需要身份认证时 跳转到这里
     */
    @RequestMapping("/auth/require")
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //拿到请求对象
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest != null){
            //获取 跳转url
            String targetUrl = savedRequest.getRedirectUrl();
            logger.info("引发跳转的请求是:"+targetUrl);

            //判断 targetUrl 是不是 .html　结尾, 如果是：跳转到登录页(返回view)
            if (StringUtils.endsWithIgnoreCase(targetUrl,".html")){
                String redirectUrl = securityProperties.getBrowser().getLoginPage();
                redirectStrategy.sendRedirect(request,response,redirectUrl);
            }

        }
        //如果不是，返回一个json 字符串
        return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
    }

}
