package com.penta.ps.license.license.exception.contract;

public class NotFoundComponentTypeException extends RuntimeException{
    public NotFoundComponentTypeException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotFoundComponentTypeException(String msg) {
        super(msg);
    }

    public NotFoundComponentTypeException() {
        super();
    }
}
