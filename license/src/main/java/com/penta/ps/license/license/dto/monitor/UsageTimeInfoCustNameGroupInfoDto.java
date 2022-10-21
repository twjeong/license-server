package com.penta.ps.license.license.dto.monitor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsageTimeInfoCustNameGroupInfoDto {
    String custNm;
    String serialNo;

    @Builder
    public UsageTimeInfoCustNameGroupInfoDto(String custNm, String serialNo){
        this.custNm = custNm;
        this.serialNo = serialNo;
    }
}
