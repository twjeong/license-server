package com.penta.ps.license.license.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class ContractFileGroupInfoDto {
    String contractFileName;
    String lisProNm;
    String lisTypeOcUp;
    Long resultCount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date lisSDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date lisEDate;

    @Builder
    public ContractFileGroupInfoDto(String contractFileName, String lisProNm, String lisTypeOcUp, Long resultCount) {
        this.contractFileName = contractFileName;
        this.lisProNm = lisProNm;
        this.lisTypeOcUp = lisTypeOcUp;
        this.resultCount = resultCount;
    }

    public ContractFileGroupInfoDto(String lisProNm, String lisTypeOcUp, Long resultCount) {
        this.lisProNm = lisProNm;
        this.lisTypeOcUp = lisTypeOcUp;
        this.resultCount = resultCount;
    }

    public ContractFileGroupInfoDto(String contractFileName, String lisProNm, String lisTypeOcUp, Long resultCount,
                                    Date lisSDate, Date lisEDate) {
        this.contractFileName = contractFileName;
        this.lisProNm = lisProNm;
        this.lisTypeOcUp = lisTypeOcUp;
        this.resultCount = resultCount;
        this.lisSDate = lisSDate;
        this.lisEDate = lisEDate;
    }
}
