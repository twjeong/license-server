package com.penta.ps.license.license.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.penta.ps.license.license.dto.contract.ContractExtendedInfoDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SingleResultWithContractExtendedField<T> extends CommonResult {
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private T startDate;

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private T endDate;

    private ContractExtendedInfoDto extendedField;
}

