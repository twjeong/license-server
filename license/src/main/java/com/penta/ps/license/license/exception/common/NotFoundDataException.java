package com.penta.ps.license.license.exception.common;

public class NotFoundDataException extends RuntimeException {
    public NotFoundDataException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundDataException(String msg) {
        super(msg);
    }

    public NotFoundDataException() {
        super();
    }
}
