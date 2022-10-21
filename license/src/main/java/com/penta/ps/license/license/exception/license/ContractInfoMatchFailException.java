package com.penta.ps.license.license.exception.license;

public class ContractInfoMatchFailException extends RuntimeException{
    public ContractInfoMatchFailException(String msg, Throwable t){
        super(msg, t);
    }

    public ContractInfoMatchFailException(String msg){
        super(msg);
    }

    public ContractInfoMatchFailException(){
        super();
    }
}
