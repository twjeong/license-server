package com.penta.ps.license.license.exception.contract;

public class AlreadyUsedContractInfoException extends RuntimeException {
    public AlreadyUsedContractInfoException(String msg, Throwable t) {
        super(msg, t);
    }

    public AlreadyUsedContractInfoException(String msg) {
        super(msg);
    }

    public AlreadyUsedContractInfoException() {
        super();
    }
}
