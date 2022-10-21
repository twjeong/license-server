package com.penta.ps.license.license.dto.license;

import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTicketCountInfoDto {
    private String contractFileName;   // 계약 정보 파일명
    private String custNm;             //고객사명
    private String orderNum;          //납품계약번호
    private String lisProNm;          //제품명
    private TypeDefine.LicenseType licenseType;          //라이선스 타입
    private long allCount;
    private long availableCount;
    private long expiredCount;
    private long usedCount = 0;

    @Getter
    @Builder
    public static class ContractTicketCountInfoDtoResp{
        CommonResult resultDto;
        List<ContractTicketCountInfoDto> contractTicketCountInfoDtos;

        @Builder
        public ContractTicketCountInfoDtoResp(CommonResult resultDto,
                                              List<ContractTicketCountInfoDto> contractTicketCountInfoDtos) {
            this.resultDto = resultDto;
            this.contractTicketCountInfoDtos = contractTicketCountInfoDtos;
        }
    }
}
