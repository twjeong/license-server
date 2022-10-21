package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ContactFindAllInfoDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ContractFileDetailInfoDto {
        @JsonProperty(value = "key_idx")
        Integer keyIdx;            //Key ID

        @JsonProperty(value = "contract_file_name")
        String contractFileName;   // 계약 정보 파일명

        @JsonProperty(value = "cust_nm")
        String custNm;             //고객사명

        @JsonProperty(value = "end_user_id")
        Integer endUserId;         //고객사 번호

        @JsonProperty(value = "esti_type")
        String estiType;           //계약 타입

        @JsonProperty(value = "order_num")
        String orderNum;          //납품계약번호

        @JsonProperty(value = "lis_s_date")
        Date lisSDate;       //라이선스 시작일

        @JsonProperty(value = "lis_e_date")
        Date lisEDate;       //라이선스 종료일

        @JsonProperty(value = "ins_s_dt")
        Date insSDt;         //서비스 시작일

        @JsonProperty(value = "ins_e_dt")
        Date insEDt;         //서비스 종료일

        @JsonProperty(value = "lis_pro_nm")
        String lisProNm;          //제품명

        @JsonProperty(value = "cust_ip")
        String custIp;          //IP (라이선스 서버 체크용)

        @JsonProperty(value = "kms_id")
        String kmsId;          //KMS SA ID

        @JsonProperty(value = "hw_sn")
        String hwSn;          //제품 ID

        @JsonProperty(value = "lo_core")
        Integer loCore;          //코어 수

        @JsonProperty(value = "vcore_cnt")
        Integer vcoreCnt;          //KMS SA 코어 수

        @JsonProperty(value = "lis_ver_up")
        String lisVerUp;          //라이선스 버전

        @JsonProperty(value = "lis_kind_up")
        String lisKindUp;          //설치 환경

        @JsonProperty(value = "lis_type_oc_up")
        String lisTypeOcUp;          //라이선스 타입

        @JsonProperty(value = "cust_node_id")
        String custNodeId;    // 제품 실행환경 식별자(WAPPLES)

        @JsonProperty(value = "lis_pro_ver_up")
        String lisProVerUp;    // 제품버전(WAPPLES)

        @JsonProperty(value = "cloud_sp")
        String cloudSp;          //CSP 종류

        @JsonProperty(value = "cloud_csi")
        String cloudCsi;          //클라우드 단품

        @JsonProperty(value = "cloud_sgn")
        String cloudSgn;          //클라우드 Autoscaling

        @JsonProperty(value = "instance_nm")
        String instanceNm;          //DP-ORA 인스턴스명

        @JsonProperty(value = "sym_api_yn")
        String symApiYn;          //대칭키 암복호화 API

        @JsonProperty(value = "pub_api_yn")
        String pubApiYn;          //비대칭키 암복호화 API

        @JsonProperty(value = "kewin_api_yn")
        String kewinApiYn;          //KE-WIN 복호화 API

        @JsonProperty(value = "api_yn")
        String apiYn;          //암복호화 API

        @JsonProperty(value = "kms_yn")
        String kmsYn;          //KMS 외부 키 연동

        @JsonProperty(value = "os_yn")
        String osYn;          //OS 계정별 암복호화 권한 제어

        @JsonProperty(value = "site_nm")
        String siteNm;          //사이트명

        @JsonProperty(value = "lis_os_type")
        String lisOsType;          //OS

        @JsonProperty(value = "km_yn")
        String kmYn;          //KM 암복호화

        @JsonProperty(value = "file_yn")
        String fileYn;          //FILE 암복호화

        @JsonProperty(value = "hash_yn")
        String hashYn;          //HASH 암복호화

        @JsonProperty(value = "kms_enc")
        String kmsEnc;          //KMS 암호화 및 BA-SCP 연동

        @JsonProperty(value = "kms_pub_key")
        String kmsPubKey;          //SCP 외부키 연동

        @JsonProperty(value = "kms_pri_key")
        String kmsPriKey;          //비공개키 연동

        @JsonProperty(value = "web_cnt")
        Integer webCnt;    // 보호 대상 웹서버 등록수 제한(WAPPLES), -1(무제한)

        @JsonProperty(value = "lis_exp_act")
        Integer lisExpAct;    // 라이선스 만료시 대응 설정(0,1,2)

        @JsonProperty(value = "license_status")
        String licenseStatus;          //티켓 사용(발급) 여부 (0:티켓미사용, 1:티켓사용)
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateLicenseStatusReq {
        @JsonProperty(value = "key_idx")
        Integer keyIdx;            //Key ID

        @JsonProperty(value = "contract_file_name")
        String contractFileName;   // 계약 정보 파일명

        @JsonProperty(value = "before_license_status")
        String beforeLicenseStatus;

        @JsonProperty(value = "after_license_status")
        String afterLicenseStatus;

        @Builder
        public UpdateLicenseStatusReq(Integer keyIdx, String contractFileName, String beforeLicenseStatus, String afterLicenseStatus) {
            this.keyIdx = keyIdx;                        //Key ID
            this.contractFileName = contractFileName;    // 계약정보 파일명
            this.beforeLicenseStatus = beforeLicenseStatus;
            this.afterLicenseStatus = afterLicenseStatus;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class FindContractFileDetailInfoDtoResp {

        @JsonProperty(value = "result")
        private Integer result;

        @JsonProperty(value = "msg")
        private String msg;

        @JsonProperty(value = "contractFileDetailInfo")
        ContractFileDetailInfoDto contractFileDetailInfoDto;

        @Builder
        public FindContractFileDetailInfoDtoResp(Integer result, String msg,
                                                 ContractFileDetailInfoDto contractFileDetailInfoDto) {
            this.result = result;
            this.msg = msg;
            this.contractFileDetailInfoDto = contractFileDetailInfoDto;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class FindContractFileDetailInfoListDtoResp {

        @JsonProperty(value = "result")
        private Integer result;

        @JsonProperty(value = "msg")
        private String msg;

        @JsonAlias({"contractRegisterGroupDetailInfoList", "contractInfoList"})
        List<ContractFileDetailInfoDto> contractFileDetailInfoDtos;

        @Builder
        public FindContractFileDetailInfoListDtoResp(Integer result, String msg,
                                                     List<ContractFileDetailInfoDto> contractFileDetailInfoDtos) {
            this.result = result;
            this.msg = msg;
            this.contractFileDetailInfoDtos = contractFileDetailInfoDtos;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ContractExtendedInfoDto {
        String serialNo;
        Integer keyIdx;            //Key ID
        String contractFileName;   // 계약 정보 파일명
        String custNm;             //고객사명
        Integer endUserId;         //고객사 번호
        String estiType;           //계약 타입
        String contractNumber;          //납품계약번호
        String startDate;       //라이선스 시작일
        String endDate;       //라이선스 종료일
        String insSDt;         //서비스 시작일
        String insEDt;         //서비스 종료일
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
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContractExtendedInfo {
        @JsonProperty(value = "result")
        private Integer result;

        @JsonProperty(value = "msg")
        private String msg;

        @JsonProperty(value = "contractExtenedInfo")
        ContractExtendedInfoDto contractExtendedInfoDto;

        @JsonIgnore
        private String startDate;

        @JsonIgnore
        private String endDate;
    }
}
