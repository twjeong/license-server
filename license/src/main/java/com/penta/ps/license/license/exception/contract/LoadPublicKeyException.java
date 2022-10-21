package com.penta.ps.license.license.exception.contract;

public class LoadPublicKeyException extends RuntimeException{
    public LoadPublicKeyException(String msg, Throwable t){
        super(msg, t);
    }

    public LoadPublicKeyException(String msg){
        super(msg);
    }

    public LoadPublicKeyException(){
        super();
    }
}
