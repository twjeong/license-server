package com.penta.ps.license.license.exception.contract;

public class ContractInputValueCheckException extends RuntimeException {
    public ContractInputValueCheckException(String msg, Throwable t) {
        super(msg, t);
    }

    public ContractInputValueCheckException(String msg) {
        super(msg);
    }

    public ContractInputValueCheckException() {
        super();
    }
}
