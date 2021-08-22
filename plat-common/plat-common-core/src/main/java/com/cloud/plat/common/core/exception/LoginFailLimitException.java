package com.cloud.plat.common.core.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * @author hyt
 * @Description <类描述>
 * @create 2021/8/21 22:26
 * @contact 269016084@qq.com
 *
 **/
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private String msg;

    public LoginFailLimitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
