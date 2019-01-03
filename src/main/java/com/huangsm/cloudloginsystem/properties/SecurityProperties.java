package com.huangsm.cloudloginsystem.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Security 属性类
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.properties
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Data
@Component
@ConfigurationProperties(prefix = "huangsm.security")
public class SecurityProperties {

    private BrowserProperties browser=new BrowserProperties();
}
