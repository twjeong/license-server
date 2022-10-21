package com.penta.ps.license.license.exception.common;

public class PostToManagerException extends RuntimeException {
    public PostToManagerException(String msg, Throwable t) {
        super(msg, t);
    }

    public PostToManagerException(String msg) {
        super(msg);
    }

    public PostToManagerException() {
        super();
    }
}
