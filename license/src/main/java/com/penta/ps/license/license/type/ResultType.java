package com.penta.ps.license.license.type;

public enum ResultType {
    SUCCESS(0),
    FAIL(1);

    private int resultType;

    ResultType(int resultType) {
        this.resultType = resultType;
    }

    public int getCode(){
        return resultType;
    }
}
