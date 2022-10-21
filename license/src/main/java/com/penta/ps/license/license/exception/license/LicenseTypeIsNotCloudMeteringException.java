package com.penta.ps.license.license.exception.license;

public class LicenseTypeIsNotCloudMeteringException extends RuntimeException{
    public LicenseTypeIsNotCloudMeteringException(String msg, Throwable t){
        super(msg, t);
    }

    public LicenseTypeIsNotCloudMeteringException(String msg){
        super(msg);
    }

    public LicenseTypeIsNotCloudMeteringException(){
        super();
    }
}
