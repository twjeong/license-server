package com.penta.ps.license.license.exception.license;

public class GetToManagerException extends RuntimeException {
    public GetToManagerException(String msg, Throwable t) {
        super(msg, t);
    }

    public GetToManagerException(String msg) {
        super(msg);
    }

    public GetToManagerException() {
        super();
    }
}
