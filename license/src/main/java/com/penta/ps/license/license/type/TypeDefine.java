package com.penta.ps.license.license.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.stereotype.Service;

@Service
public class TypeDefine {
    /*
    public enum ComponentType {
        NOT_FOUND(-9000),
        BA_SCP(1),
        BA_UND(2),
        DP_ORA(3),
        DP_MSQ(4),
        DE_MYQ(5),
        DA(6),
        KE_WIN(7),
        KE_LNX(8),
        SG_KMS(9);

        private int componentType;

        ComponentType(int componentType) {
            this.componentType = componentType;
        }

        public int getCode(){
            return componentType;
        }

        public static boolean isComponentType(ComponentType typeName){
            ComponentType[] componentTypes = ComponentType.values();
            for(ComponentType tempComponentType : componentTypes) {
                if(tempComponentType == typeName) {
                    return true;
                }
            }
            return false;
        }
    }
    */
    public enum ProductName {
        //        NOT_FOUND(-9000, "NOT-FOUND ProductName", "NOT-FOUND ProductName"),
        BA_SCP(1, "BA-SCP", "D'Amo BA-SCP"),
        DAmo_KE(2, "DAmo_KE", "D'Amo KE-WIN"),
        DE_MYQ(3, "DE-MYQ", "D'Amo DE-MYQ"),
        DP_MSQ(4, "DP-MSQ", "D'Amo DP-MSQ"),
        DP_ORA(5, "DP-ORA", "D'Amo DP-ORA"),
        BA_UND(6, "BA-UND", "D'Amo BA-UND"),
        BA_P11(7, "BA-P11", "D'Amo BA-P11"),
        BA_POS(8, "BA-POS", "D'Amo BA-POS"),
        KE_LNX(9, "KE-LNX", "D'Amo KE-LNX"),
        DA(10, "DA", "D'Amo DA"),
        SG_KMS(11, "SG-KMS", "D'Amo SG-KMS"),
        SG_KMS_SA(12, "SG-KMS_SA", "D'Amo SG-KMS SA"),
        DE_PGS(13, "DE-PGS", "D'Amo DE-PGS"),
        WAPPLES(14, "WAPPLES", "WAPPLES"),
        ISIGN(15, "ISIGN", "ISIGN");

        private int productIdx;
        private String orgProductName;
        private String newProductName;

        ProductName(int productIdx, String orgProductName, String newProductName) {
            this.productIdx = productIdx;
            this.orgProductName = orgProductName;
            this.newProductName = newProductName;
        }

        public int getCode() {
            return productIdx;
        }

        public String getOrgName() {
            return orgProductName;
        }

        public String getNewName() {
            return newProductName;
        }

        public static String getNewProductName(int productIdx) {
            for (ProductName productName : ProductName.values()) {
                if (productName.productIdx == productIdx) {
                    return productName.newProductName;
                }
            }
            return null;
        }

        public static String getNewProductName(String orgProductName) {
            for (ProductName productName : ProductName.values()) {
                if (productName.orgProductName.contentEquals(orgProductName)) {
                    return productName.newProductName;
                }
            }
            return null;
        }

        public static String getProductName(String newProductName) {
            for (ProductName productName : ProductName.values()) {
                if (productName.newProductName.contentEquals(newProductName)) {
                    return productName.name();
                }
            }
            return null;
        }

        public static String getFromNewProductNameToOrgProductName(String newProductName) {
            for (ProductName productName : ProductName.values()) {
                if (productName.newProductName.contentEquals(newProductName)) {
                    return productName.orgProductName;
                }
            }
            return null;
        }
    }

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

    public enum BooleanType {
        //NOT_FOUND("-9000", "NOT FOUND"),
        FALSE("0", "false"),
        TRUE("1", "true");

        private final String flagType;
        private final String flagName;

        BooleanType(String flagType, String flagName) {
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
            for (BooleanType booleanType : BooleanType.values()) {
                if (booleanType.flagType.contentEquals(flagType)) {
                    return booleanType.flagName;
                }
            }
            //return BooleanType.NOT_FOUND.flagName;
            return null;
        }
    }

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

        LicenseType(int licenseType, String licenseName) {
            this.licenseType = licenseType;
            this.licenseName = licenseName;
        }

        public int getCode() {
            return this.licenseType;
        }

        public String getName() {
            return this.licenseName;
        }

        public static String getName(int licenseType) {
            for (LicenseType licenseType1 : LicenseType.values()) {
                if (licenseType1.licenseType == licenseType) {
                    return licenseType1.licenseName;
                }
            }
            //return LicenseType.NOT_FOUND.licenseName;
            return null;
        }

        public static String getName(String licenseName) {
            for (LicenseType licenseType1 : LicenseType.values()) {
                if (licenseType1.licenseName.contentEquals(licenseName)) {
                    return licenseType1.licenseName;
                }
            }
            //return LicenseType.NOT_FOUND.licenseName;
            return null;
        }

        public static String getName(LicenseType licenseType) {
            for (LicenseType licenseType1 : LicenseType.values()) {
                if (licenseType1.equals(licenseType)) {
                    return licenseType1.licenseName;
                }
            }
            //return LicenseType.NOT_FOUND.licenseName;
            return null;
        }

        public static LicenseType getLicenseType(int licenseType) {
            for (LicenseType licenseType1 : LicenseType.values()) {
                if (licenseType1.licenseType == licenseType) {
                    return licenseType1;
                }
            }
            //return LicenseType.NOT_FOUND.licenseName;
            return null;
        }

        public static LicenseType getLicenseType(String licenseName) {
            for (LicenseType licenseType1 : LicenseType.values()) {
                if (licenseType1.name().contentEquals(licenseName)) {
                    return licenseType1;
                }
            }
            //return LicenseType.NOT_FOUND.licenseName;
            return null;
        }
    }

    public enum Status {
        NOT_FOUND(-9000, "Not found Status"),
        REGISTER_COMPONENT(1, "register-component"),
        ISSUE_LICENSE(2, "issue-license"),
        LICENSE_IN_USE(3, "license-in-use"),
        UNUSED_LICENSE(4, "unused-license"),
        DUPLICATED(5, "duplicated");

        private int statusCode;
        private String statusName;

        Status(int status, String statusName) {
            this.statusCode = statusCode;
            this.statusName = statusName;
        }

        public int getCode() {
            return statusCode;
        }

        public String getName() {
            return statusName;
        }

        public static String getName(int statusCode) {
            for (Status status : Status.values()) {
                if (status.statusCode == statusCode) {
                    return status.statusName;
                }
            }
            return null;
        }

        public static String getName(String statusName) {
            for (Status status : Status.values()) {
                if (status.statusName.contentEquals(statusName)) {
                    return status.statusName;
                }
            }
            return null;
        }

        public static String getname(Status status) {
            for (Status status1 : Status.values()) {
                if (status1.equals(status)) {
                    return status1.statusName;
                }
            }
            return null;
        }

        public static Status getStatus(String statusName) {
            for (Status status : Status.values()) {
                if (status.statusName.contentEquals(statusName)) {
                    return status;
                }
            }
            return null;
        }
    }

    public enum BillingType {
        INSTANCE(0),
        BASE(1),
        FIXED(2);

        private int billingType;

        BillingType(int billingType) {
            this.billingType = billingType;
        }

        public int getCode(){
            return billingType;
        }

        public static boolean isBillingType(BillingType typeName){
            BillingType[] billingType = BillingType.values();
            for(BillingType tempBillingType : billingType) {
                if(tempBillingType == typeName) {
                    return true;
                }
            }
            return false;
        }
    }
}
