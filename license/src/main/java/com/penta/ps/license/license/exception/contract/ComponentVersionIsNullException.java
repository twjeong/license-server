package com.penta.ps.license.license.exception.contract;

public class ComponentVersionIsNullException extends RuntimeException{
    public ComponentVersionIsNullException(String msg, Throwable t) {
        super(msg, t);
    }

    public ComponentVersionIsNullException(String msg) {
        super(msg);
    }

    public ComponentVersionIsNullException() {
        super();
    }
}
