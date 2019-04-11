package com.corsair.sparrow.pirate.oauth.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author jack
 * 二维码Exception
 */
public class QrCodeException extends AuthenticationException {

    public QrCodeException(String msg) {
        super(msg);
    }

    public QrCodeException(String msg, Throwable t) {
        super(msg, t);
    }
}
