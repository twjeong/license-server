package com.penta.ps.license.license.type;

public enum ExpireType {
    VALID(0),
    TO_EXPIRE(1),
    EXPIRE(2);

    private final int expireType;

    ExpireType(int expireType) {
        this.expireType = expireType;
    }

    public int getCode(){
        return expireType;
    }
}
