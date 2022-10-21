package com.penta.ps.license.license.type.contract;

public enum BooleanType {
    //NOT_FOUND("-9000", "NOT FOUND"),
    FALSE("0", false),
    TRUE("1", true);

    private final String flagType;
    private final Boolean flagName;

    BooleanType(String flagType, Boolean flagName) {
        this.flagType = flagType;
        this.flagName = flagName;
    }

    public String getCode() {
        return this.flagType;
    }

    public Boolean getName() {
        return this.flagName;
    }

    public static Boolean getName(String flagType) {
        for (BooleanType booleanType : BooleanType.values()) {
            if (booleanType.flagType.contentEquals(flagType)) {
                return booleanType.flagName;
            }
        }
        //return BooleanType.NOT_FOUND.flagName;
        return null;
    }
}
