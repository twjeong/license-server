package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

public class LicenseCertificateDto {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Optional{
        int SV;
        int AC;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LicenseCertificate{
        String licenseId;
        String nodeId;
        String productId;
        String productName;
        String productVersion;
        String contractNumber;
        String licenseType;
        int cpuCount;
        LicenseCertificateDto.Optional optional;
    }

}
