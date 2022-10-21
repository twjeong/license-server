package com.penta.ps.license.license.type.contract;

public enum ThreeCaseFlagType {
    //NOT_FOUND("-9000", "NOT-FOUND ThreeCaseFlagType"),
    Off("0", "Off"),
    Dec("1", "Dec"),
    All("2", "All");

    private final String flagType;
    private final String flagTypeName;

    ThreeCaseFlagType(String flagType, String flagTypeName) {
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
        for (ThreeCaseFlagType threeCaseFlagType : ThreeCaseFlagType.values()) {
            if (threeCaseFlagType.flagType.contentEquals(flagType))
                return threeCaseFlagType.flagTypeName;
        }
        //return ThreeCaseFlagType.NOT_FOUND.flagTypeName;
        return null;
    }
}
