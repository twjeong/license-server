package com.penta.ps.license.license.exception.common;

public class DatabaseViolationException extends RuntimeException {
    public DatabaseViolationException(String msg, Throwable t) {
        super(msg, t);
    }

    public DatabaseViolationException(String msg) {
        super(msg);
    }

    public DatabaseViolationException() {
        super();
    }
}
