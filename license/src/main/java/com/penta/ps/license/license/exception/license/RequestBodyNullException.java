package com.penta.ps.license.license.exception.license;

public class RequestBodyNullException extends RuntimeException {
    public RequestBodyNullException(String msg, Throwable t){
        super(msg, t);
    }

    public RequestBodyNullException(String msg){
        super(msg);
    }

    public RequestBodyNullException(){
        super();
    }
}
