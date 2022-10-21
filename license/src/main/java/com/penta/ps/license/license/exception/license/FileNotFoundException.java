package com.penta.ps.license.license.exception.license;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public FileNotFoundException(String msg) {
        super(msg);
    }

    public FileNotFoundException() {
        super();
    }
}
