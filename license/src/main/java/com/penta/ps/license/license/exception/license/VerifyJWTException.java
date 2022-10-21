package com.penta.ps.license.license.exception.license;

public class VerifyJWTException extends RuntimeException{
    public VerifyJWTException(String msg, Throwable t){
        super(msg, t);
    }

    public VerifyJWTException(String msg){
        super(msg);
    }

    public VerifyJWTException(){
        super();
    }
}
