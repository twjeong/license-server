package com.penta.ps.license.license.exception.common;

public class EtcException extends RuntimeException {
    public EtcException(String msg, Throwable t) {
        super(msg, t);
    }

    public EtcException(String msg) {
        super(msg);
    }

    public EtcException() {
        super();
    }

}
