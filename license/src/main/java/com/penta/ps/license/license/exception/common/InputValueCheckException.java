package com.penta.ps.license.license.exception.common;

public class InputValueCheckException extends RuntimeException {
    public InputValueCheckException(String msg, Throwable t) {
        super(msg, t);
    }

    public InputValueCheckException(String msg) {
        super(msg);
    }

    public InputValueCheckException() {
        super();
    }
}
