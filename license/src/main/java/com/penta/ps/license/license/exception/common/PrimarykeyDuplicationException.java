package com.penta.ps.license.license.exception.common;

public class PrimarykeyDuplicationException extends RuntimeException {
    public PrimarykeyDuplicationException(String msg, Throwable t) {
        super(msg, t);
    }

    public PrimarykeyDuplicationException(String msg) {
        super(msg);
    }

    public PrimarykeyDuplicationException() {
        super();
    }
}
