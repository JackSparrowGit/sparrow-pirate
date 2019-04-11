package com.corsair.sparrow.pirate.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jack
 * 手机号验证码不匹配Exception
 */
public class PhoneCodeNotMatchException extends AuthenticationException {

    public PhoneCodeNotMatchException(String msg) {
        super(msg);
    }

    public PhoneCodeNotMatchException(String msg, Throwable t) {
        super(msg, t);
    }
}
