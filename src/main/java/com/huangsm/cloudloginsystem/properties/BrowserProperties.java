package com.huangsm.cloudloginsystem.properties;

import lombok.Data;

/**
 * browser(浏览器)配置文件里的： huangsm.security.browser.loginPage 属性类
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.properties
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Data
public class BrowserProperties {
    /**
     *  loginPage 默认值  是login.html
     *  如果 application.properties 里有对 fantJ.security.browser.loginPage 的声明，则获取该值
     */
    private String loginPage = "/browser-login.html";

    /**
     * 默认 返回 json 类型
     */
    private LoginType loginType = LoginType.JSON;

}
