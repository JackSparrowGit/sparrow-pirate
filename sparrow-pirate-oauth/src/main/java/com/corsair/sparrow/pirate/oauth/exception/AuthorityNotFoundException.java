package com.corsair.sparrow.pirate.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jack
 * 权限找不到Exception
 */
public class AuthorityNotFoundException extends AuthenticationException {

    public AuthorityNotFoundException(String msg) {
        super(msg);
    }

    public AuthorityNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
