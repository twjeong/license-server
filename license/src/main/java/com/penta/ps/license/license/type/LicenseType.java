package com.penta.ps.license.license.type;

public enum LicenseType {
    NOT_FOUND(-9000, "NOT FOUND LicenseType"),
    COMMERCIAL(1, "commercial"),
    CLOUD_INSTANCE(2, "cloud-instance"),
    CLOUD_METERING(3, "cloud-metering"),
    CORE(4, "core"),
    SUBSCRIPTION(5, "subscription"),
    BUNDLE(6, "bundle"),
    VOLUME(7, "volume"),
    DEV(8, "dev"),
    TEST(9, "test"),
    CLOUD_CORE(10, "cloud-core"),
    CLOUD_SCALE(11, "cloud-scale"),
    COMMON_COMMERCIAL(12, "common-commercial");

    private int licenseType;
    private String licenseName;

    LicenseType(int licenseType, String licenseName){
        this.licenseType = licenseType;
        this.licenseName = licenseName;
    }

    public int getCode() {
        return this.licenseType;
    }

    public String getName() {
        return this.licenseName;
    }

    public static String getName(int licenseType){
        for(LicenseType licenseType1 : LicenseType.values()){
            if(licenseType1.licenseType == licenseType){
                return licenseType1.licenseName;
            }
        }
        //return LicenseType.NOT_FOUND.licenseName;
        return null;
    }

    public static String getName(String name){
        for(LicenseType licenseType1 : LicenseType.values()){
            if(licenseType1.name().contentEquals(name)){
                return licenseType1.name();
            }
        }
        //return LicenseType.NOT_FOUND.licenseName;
        return null;
    }

    public static int getEnumTypeFromLicenseName(String name){
        for(LicenseType licenseType1 : LicenseType.values()){
            if(licenseType1.licenseName.contentEquals(name)){
                return licenseType1.ordinal();
            }
        }
        //return LicenseType.NOT_FOUND.licenseName;
        return -9000;
    }
}
