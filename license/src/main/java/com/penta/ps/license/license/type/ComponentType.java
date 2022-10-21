package com.penta.ps.license.license.type;

public enum ComponentType {
    //    NOT_FOUND(-9000, "NOT-FOUND ProductName", "NOT-FOUND ProductName"),
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

    ComponentType(int productIdx, String orgProductName, String newProductName) {
        this.productIdx = productIdx;
        this.orgProductName = orgProductName;
        this.newProductName = newProductName;
    }

    public static String getNewProductName(int productIdx) {
        for (ComponentType productName : ComponentType.values()) {
            if (productName.productIdx == productIdx) {
                return productName.newProductName;
            }
        }
        return null;
    }

    public static String getNewProductNameFromEnumName(String enumName) {
        for (ComponentType productName : ComponentType.values()) {
            if (productName.name().contentEquals(enumName)) {
                return productName.newProductName;
            }
        }
        return null;
    }

    public static String getNewProductName(String orgProductName) {
        for (ComponentType productName : ComponentType.values()) {
            if (productName.orgProductName.contentEquals(orgProductName)) {
                return productName.newProductName;
            }
        }
        return null;
    }

    public static String getName(String orgProductName) {
        for (ComponentType productName : ComponentType.values()) {
            if (productName.orgProductName.contentEquals(orgProductName)) {
                return productName.name();
            }
        }
        return null;
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
}
