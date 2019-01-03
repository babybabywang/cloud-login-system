package com.huangsm.cloudloginsystem.utils;

import lombok.Data;

/**
 * 返回类 工具(可返回任意类型的结果)
 * @author huang
 * @PACKAGE_NAME com.huangsm.cloudloginsystem.utils
 * @PROJECT_NAME cloud-login-system
 * @date 2019/1/3
 */
@Data
public class SimpleResponse {
    /**
     * 返回 内容(json格式)
     */
    private Object content;

    public SimpleResponse(Object content) {
        this.content = content;
    }
}
