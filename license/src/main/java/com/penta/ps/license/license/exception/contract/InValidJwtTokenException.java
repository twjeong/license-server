package com.penta.ps.license.license.exception.contract;

public class InValidJwtTokenException extends RuntimeException {
    public InValidJwtTokenException(String msg, Throwable t) {
            super(msg, t);
        }

    public InValidJwtTokenException(String msg) {
            super(msg);
        }

    public InValidJwtTokenException() {
            super();
        }
}
