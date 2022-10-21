package com.penta.ps.license.license.dto.license;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ContractCloudServiceDto {
    String componentName;
    Long sumOfUsageTime;
}
