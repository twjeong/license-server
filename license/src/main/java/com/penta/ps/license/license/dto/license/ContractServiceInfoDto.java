package com.penta.ps.license.license.dto.license;

import com.penta.ps.license.license.type.TypeDefine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContractServiceInfoDto {
    String customer;
    TypeDefine.LicenseType licenseType;
    String componentName;
    Long issuedCount;
    Long inUseCount;
    Long sumOfUsageTime;
}
