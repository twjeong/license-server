package com.penta.ps.license.license.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractExtendedInfoDto {
    String serialNo;
    Integer keyIdx;            //Key ID
    String contractFileName;   // 계약 정보 파일명
    String custNm;             //고객사명
    Integer endUserId;         //고객사 번호
    String estiType;           //계약 타입
    String contractNumber;          //납품계약번호

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    Date startDate;       //라이선스 시작일

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    Date endDate;       //라이선스 종료일

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    Date insSDt;         //서비스 시작일

    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    Date insEDt;         //서비스 종료일

    String productName;          //제품명
    String validIp;          //IP (라이선스 서버 체크용)
    String sgkmsId;          //KMS SA ID
    String hardwareId;       // KMS HW ID(제품 ID) hwSn??
    Integer cpuCount;          //코어 수
    Integer vcoreCnt;          //KMS SA 코어 수
    String version;          //라이선스 버전
    String lisKindUp;          //설치 환경
    String type;          //라이선스 타입
    String cloudServiceProvider;          //CSP 종류
    String cloudServiceID;          //클라우드 단품
    String cloudScaleGroupName;          //클라우드 Autoscaling
    String instanceName;          //DP-ORA 인스턴스명
    String optionSymmetricKeyEncryptionAPI;          //대칭키 암복호화 API
    String optionPublicKeyEncryptionAPI;          //비대칭키 암복호화 API
    Boolean optionKeWinAPI;          //KE-WIN 복호화 API
    Boolean optionEncryptionAPI;          //암복호화 API
    Boolean optionExportKey;          //KMS 외부 키 연동
    Boolean optionEncryptionPrivilege;          //OS 계정별 암복호화 권한 제어
    String siteNm;          //사이트명
    String optionBundleOs;          //OS
    Boolean optionBundleKms;          //KM 암복호화
    Boolean optionBundleFile;          //FILE 암복호화
    Boolean optionBundleHash;          //HASH 암복호화
    Boolean functionApplyService;          //KMS 암호화 및 BA-SCP 연동
    Boolean functionApplyExternalKey;          //SCP 외부키 연동
    Boolean functionApplyAsymKey;          //비공개키 연동
    String subProductName;    // DE-MYQ 세부제품명("DE-MYQ" 하드코딩, 기획 김교남팀장 확인)
    Integer dbCountLimit;    // DE-MYQ 암호화가능 DB개수("2000" 하드코딩, 기획 김교남팀장 확인)
    Integer webCnt;
    Integer lisExpAct;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContractExtendedInfo {
        ContractInfoJsonDto.ResultDto resultDto;

        ContractExtendedInfoDto contractExtendedInfoDto;

        @JsonIgnore
        private String startDate;

        @JsonIgnore
        private String endDate;

        public ContractExtendedInfo(ContractInfoJsonDto.ResultDto resultDto, ContractExtendedInfoDto contractExtendedInfoDto){
            this.resultDto = resultDto;
            this.contractExtendedInfoDto = contractExtendedInfoDto;
        }
    }

}
