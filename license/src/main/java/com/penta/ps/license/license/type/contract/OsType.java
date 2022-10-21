package com.penta.ps.license.license.type.contract;

public enum OsType {
    //NOT_FOUND("-9000", "NOT FOUND"),
    UNIX("0", "Unix"),
    WINDOWS("1", "Windows"),
    LINUX("2", "Linux");

    private String flagType;
    private String flagName;

    OsType(String flagType, String flagName) {
        this.flagType = flagType;
        this.flagName = flagName;
    }

    public String getCode() {
        return this.flagType;
    }

    public String getName() {
        return this.flagName;
    }

    public static String getName(String flagType) {
        for (OsType osType : OsType.values()) {
            if (osType.flagType.contentEquals(flagType))
                return osType.flagName;
        }
        //return OsType.NOT_FOUND.getName();
        return null;
    }
}
