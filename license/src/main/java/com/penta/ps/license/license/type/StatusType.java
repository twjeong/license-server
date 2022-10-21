package com.penta.ps.license.license.type;

public enum StatusType {
    FIRST_LOGIN(100);

    private int statusType;

    StatusType(int statusType) {
        this.statusType = statusType;
    }

    public int getCode(){
        return statusType;
    }
}
