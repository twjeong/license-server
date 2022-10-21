package com.penta.ps.license.license.exception.license;

public class ResponseBodyNullException extends RuntimeException{
    public ResponseBodyNullException(String msg, Throwable t){
        super(msg, t);
    }

    public ResponseBodyNullException(String msg){
        super(msg);
    }

    public ResponseBodyNullException(){
        super();
    }
}
