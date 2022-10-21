package com.penta.ps.license.license.exception.license;

public class AlreadyExistsDataException extends RuntimeException {
    public AlreadyExistsDataException(String msg, Throwable t){
        super(msg, t);
    }

    public AlreadyExistsDataException(String msg){
        super(msg);
    }

    public AlreadyExistsDataException(){
        super();
    }
}
