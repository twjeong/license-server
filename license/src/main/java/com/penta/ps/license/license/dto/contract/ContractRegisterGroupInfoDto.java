package com.penta.ps.license.license.dto.contract;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractRegisterGroupInfoDto {
    String custNm;             //고객사명
    Integer endUserId;         //고객사 번호
    String estiType;           //계약 타입
    String orderNum;          //납품계약번호
    String startDate;       //라이선스 시작일
    String endDate;       //라이선스 종료일
    String lisProNm;          //제품명
    String kmsId;          //KMS SA ID
    String hwSn;          //제품 ID
    Integer cpuCount;          //코어 수
    String lisVerUp;          //라이선스 버전
    String lisTypeOcUp;          //라이선스 타입
    String cloudSp;          //CSP 종류
    String cloudCsi;          //클라우드 단품
    String cloudSgn;          //클라우드 Autoscaling
    String instanceNm;          //DP-ORA 인스턴스명
    String symApiYn;          //대칭키 암복호화 API
    String pubApiYn;          //비대칭키 암복호화 API
    String kewinApiYn;          //KE-WIN 복호화 API
    String apiYn;          //암복호화 API
    String kmsYn;          //KMS 외부 키 연동
    String osYn;          //OS 계정별 암복호화 권한 제어
    String siteNm;
    String lisOsType;          //OS
    String kmYn;          //KM 암복호화
    String fileYn;          //FILE 암복호화
    String hashYn;          //HASH 암복호화
    String kmsEnc;          //KMS 암호화 및 BA-SCP 연동
    String kmsPubKey;          //SCP 외부키 연동
    String kmsPriKey;          //비공개키 연동
    Integer webCnt;
    Integer lisExpAct;

    private long allCount;
    private long availableCount;
    private long expiredCount;
    private long usedCount;

    @Getter
    @Builder
    public static class ContractRegisterGroupInfoDtoResp {
        ContractInfoJsonDto.ResultDto resultDto;
        List<ContractRegisterGroupInfoDto> contractRegisterGroupInfoDtos;

        @Builder
        public ContractRegisterGroupInfoDtoResp(ContractInfoJsonDto.ResultDto resultDto,
                                                List<ContractRegisterGroupInfoDto> contractRegisterGroupInfoDtos) {
            this.resultDto = resultDto;
            this.contractRegisterGroupInfoDtos = contractRegisterGroupInfoDtos;
        }
    }
}
