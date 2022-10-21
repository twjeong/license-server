package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.license.LicenseInfoDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseLicense {
    LicenseInfoDto[] licenseList;

    @Builder
    public ResponseLicense(LicenseInfoDto[] licenseList) {
        this.licenseList = licenseList;
    }
}
