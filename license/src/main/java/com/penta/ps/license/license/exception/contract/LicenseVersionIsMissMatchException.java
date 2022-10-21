package com.penta.ps.license.license.exception.contract;

public class LicenseVersionIsMissMatchException extends RuntimeException{
    public LicenseVersionIsMissMatchException(String msg, Throwable t){
        super(msg, t);
    }

    public LicenseVersionIsMissMatchException(String msg){
        super(msg);
    }

    public LicenseVersionIsMissMatchException(){
        super();
    }
}
