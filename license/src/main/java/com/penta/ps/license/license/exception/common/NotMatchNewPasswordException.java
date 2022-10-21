package com.penta.ps.license.license.exception.common;

public class NotMatchNewPasswordException extends RuntimeException {
    public NotMatchNewPasswordException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotMatchNewPasswordException(String msg) {
        super(msg);
    }

    public NotMatchNewPasswordException() {
        super();
    }
}
