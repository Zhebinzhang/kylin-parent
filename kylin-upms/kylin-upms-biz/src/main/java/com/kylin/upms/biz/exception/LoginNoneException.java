package com.kylin.upms.biz.exception;

import org.springframework.security.access.AccessDeniedException;

public class LoginNoneException extends AccessDeniedException {
    public LoginNoneException(String msg, Throwable t) {
        super(msg, t);
    }
    public LoginNoneException(String msg) {
        super(msg);
    }
}
