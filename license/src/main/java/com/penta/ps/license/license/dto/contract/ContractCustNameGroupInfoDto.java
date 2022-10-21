package com.penta.ps.license.license.dto.contract;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractCustNameGroupInfoDto {
    String custName;
    String lisProNm;
    String lisTypeOcUp;
    Long groupCount;

    public ContractCustNameGroupInfoDto(String custName, String lisProNm, String lisTypeOcUp, Long groupCount) {
        this.custName = custName;
        this.lisProNm = lisProNm;
        this.lisTypeOcUp = lisTypeOcUp;
        this.groupCount = groupCount;
    }

    public ContractCustNameGroupInfoDto(String custName, String lisProNm) {
        this.custName = custName;
        this.lisProNm = lisProNm;
    }

    public ContractCustNameGroupInfoDto(String lisProNm, String lisTypeOcUp, Long groupCount) {
        this.lisProNm = lisProNm;
        this.lisTypeOcUp = lisTypeOcUp;
        this.groupCount = groupCount;
    }
}
