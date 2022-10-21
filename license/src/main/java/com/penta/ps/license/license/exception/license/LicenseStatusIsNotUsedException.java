package com.penta.ps.license.license.exception.license;

public class LicenseStatusIsNotUsedException extends RuntimeException{
    public LicenseStatusIsNotUsedException(String msg, Throwable t){
        super(msg, t);
    }

    public LicenseStatusIsNotUsedException(String msg){
        super(msg);
    }

    public LicenseStatusIsNotUsedException(){
        super();
    }
}
