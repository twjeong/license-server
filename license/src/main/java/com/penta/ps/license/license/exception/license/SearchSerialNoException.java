package com.penta.ps.license.license.exception.license;

public class SearchSerialNoException extends RuntimeException{
    public SearchSerialNoException(String msg, Throwable t){
        super(msg, t);
    }

    public SearchSerialNoException(String msg){
        super(msg);
    }

    public SearchSerialNoException(){
        super();
    }
}
