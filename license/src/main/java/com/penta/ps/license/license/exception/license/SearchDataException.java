package com.penta.ps.license.license.exception.license;

public class SearchDataException extends RuntimeException {
    public SearchDataException(String msg, Throwable t){
        super(msg, t);
    }

    public SearchDataException(String msg){
        super(msg);
    }

    public SearchDataException(){
        super();
    }
}
