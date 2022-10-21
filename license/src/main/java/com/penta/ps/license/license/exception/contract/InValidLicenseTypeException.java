package com.penta.ps.license.license.exception.contract;

public class InValidLicenseTypeException extends RuntimeException{
    public InValidLicenseTypeException(String msg, Throwable t) {
        super(msg, t);
    }

    public InValidLicenseTypeException(String msg) {
        super(msg);
    }

    public InValidLicenseTypeException() {
        super();
    }
}
