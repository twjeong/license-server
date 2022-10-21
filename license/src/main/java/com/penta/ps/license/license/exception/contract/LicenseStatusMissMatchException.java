package com.penta.ps.license.license.exception.contract;

public class LicenseStatusMissMatchException extends RuntimeException {
    public LicenseStatusMissMatchException(String msg, Throwable t) {
        super(msg, t);
    }

    public LicenseStatusMissMatchException(String msg) {
        super(msg);
    }

    public LicenseStatusMissMatchException() {
        super();
    }
}
