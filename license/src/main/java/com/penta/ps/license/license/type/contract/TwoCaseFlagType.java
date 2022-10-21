package com.penta.ps.license.license.type.contract;

public enum TwoCaseFlagType {
    //NOT_FOUND("-9000", "NOT-FOUND ThreeCaseFlagType"),
    Off("0", "Off"),
    All("1", "All");
    //Dec("1", "Dec"),
    //All("2", "All");

    private final String flagType;
    private final String flagTypeName;

    TwoCaseFlagType(String flagType, String flagTypeName) {
        this.flagType = flagType;
        this.flagTypeName = flagTypeName;
    }

    public String getCode() {
        return this.flagType;
    }

    public String getName() {
        return this.flagTypeName;
    }

    public static String getName(String flagType) {
        for (TwoCaseFlagType twoCaseFlagType : TwoCaseFlagType.values()) {
            if (twoCaseFlagType.flagType.contentEquals(flagType))
                return twoCaseFlagType.flagTypeName;
        }
        return null;
    }
}
