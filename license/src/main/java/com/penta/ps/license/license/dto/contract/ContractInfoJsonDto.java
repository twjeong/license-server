package com.penta.ps.license.license.dto.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ContractInfoJsonDto {

    //==================================== Request Class
    @Getter
    @Setter
    public static class PageProcessDtoReq {
        private Integer currentPageNo;
        private Integer recordsPerPage;

        @Builder
        public PageProcessDtoReq(Integer currentPageNo, Integer recordsPerPage) {
            this.currentPageNo = currentPageNo;
            this.recordsPerPage = recordsPerPage;
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    public static class InnerContractInfoJsonDto {
        @JsonProperty(value = "key_idx")
        Integer keyIdx;            //Key ID

        @JsonProperty(value = "contract_file_name")
        String contractFileName;  // 계약정보 파일명

        @JsonProperty(value = "cust_nm")
        String custNm;           //고객사명

        @JsonProperty(value = "order_num")
        String orderNum;         //납품계약번호

        @JsonProperty(value = "lis_pro_nm")
        String lisProNm;        //제품명

        @JsonProperty(value = "lis_type_oc_up")
        String lisTypeOcUp;    //라이선스 타입

        @JsonProperty(value = "core_count")
        Integer coreCount;           //코어 수

        @JsonProperty(value = "lis_start_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisStartDate;        //라이선스 시작일

        @JsonProperty(value = "lis_end_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisEndDate;        //라이선스 종료일

        @JsonProperty(value = "license_status")
        String licenseStatus;       // 티켓 사용(발급) 여부(0:티켓미사용, 1:티켓사용)

        @JsonProperty(value = "contract_info_json")
        String contractInfoJson;  // 계약정보 json

        @Builder
        public InnerContractInfoJsonDto(Integer keyIdx, String contractFileName,
                                        String custNm, String orderNum,
                                        String lisProNm, String lisTypeOcUp, Integer coreCount,
                                        Date lisStartDate, Date lisEndDate,
                                        String licenseStatus, String contractInfoJson) {
            this.keyIdx = keyIdx;           //Key ID
            this.contractFileName = contractFileName;
            this.custNm = custNm;
            this.orderNum = orderNum;
            this.lisProNm = lisProNm;
            this.lisTypeOcUp = lisTypeOcUp;
            this.coreCount = coreCount;
            this.lisStartDate = lisStartDate;
            this.lisEndDate = lisEndDate;
            this.licenseStatus = licenseStatus;
            this.contractInfoJson = contractInfoJson;  // 계약정보 Json
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContractInfoJsonDataDto {
        @JsonProperty(value = "key_idx")
        Integer keyIdx;           //Key ID

        @JsonProperty(value = "contract_file_name")
        String contractFileName;  // 계약정보 파일명

        @JsonProperty(value = "cust_nm")
        String custNm;           //고객사명

        @JsonProperty(value = "end_user_id")
        Integer endUserId;       //고객사 번호

        @JsonProperty(value = "esti_type")
        String estiType;         //계약 타입

        @JsonProperty(value = "order_num")
        String orderNum;         //납품계약번호

        @JsonProperty(value = "lis_s_date")
        String lisSDate;        //라이선스 시작일

        @JsonProperty(value = "lis_e_date")
        String lisEDate;        //라이선스 종료일

        @JsonProperty(value = "ins_s_dt")
        String insSDt;          //서비스 시작일

        @JsonProperty(value = "ins_e_dt")
        String insEDt;          //서비스 종료일

        @JsonProperty(value = "lis_pro_nm")
        String lisProNm;        //제품명

        @JsonProperty(value = "cust_ip")
        String custIp;           //IP (라이선스 서버 체크용)

        @JsonProperty(value = "kms_id")
        String kmsId;            //KMS SA ID

        @JsonProperty(value = "hw_sn")
        String hwSn;             //제품 ID

        @JsonProperty(value = "lo_core")
        Integer loCore;           //코어 수

        @JsonProperty(value = "vcore_cnt")
        Integer vcoreCnt;         //KMS SA 코어 수

        @JsonProperty(value = "lis_ver_up")
        String lisVerUp;        //라이선스 버전

        @JsonProperty(value = "lis_kind_up")
        String lisKindUp;       //설치 환경

        @JsonProperty(value = "lis_type_oc_up")
        String lisTypeOcUp;    //라이선스 타입

        @JsonProperty(value = "cust_node_id")
        String custNodeId;    // 제품 실행환경 식별자(WAPPLES)

        @JsonProperty(value = "lis_pro_ver_up")
        String lisProVerUp;    // 제품버전(WAPPLES)

        @JsonProperty(value = "cloud_sp")
        String cloudSp;          //CSP 종류

        @JsonProperty(value = "cloud_csi")
        String cloudCsi;         //클라우드 단품

        @JsonProperty(value = "cloud_sgn")
        String cloudSgn;         //클라우드 Autoscaling

        @JsonProperty(value = "instance_nm")
        String instanceNm;       //DP-ORA 인스턴스명

        @JsonProperty(value = "sym_api_yn")
        String symApiYn;        //대칭키 암복호화 API

        @JsonProperty(value = "pub_api_yn")
        String pubApiYn;        //비대칭키 암복호화 API

        @JsonProperty(value = "kewin_api_yn")
        String kewinApiYn;     //KE-WIN 복호화 API

        @JsonProperty(value = "api_yn")
        String apiYn;            //암복호화 API

        @JsonProperty(value = "kms_yn")
        String kmsYn;            //KMS 외부 키 연동

        @JsonProperty(value = "os_yn")
        String osYn;             //OS 계정별 암복호화 권한 제어

        @JsonProperty(value = "site_nm")
        String siteNm;           //사이트명

        @JsonProperty(value = "lis_os_type")
        String lisOsType;       //OS

        @JsonProperty(value = "km_yn")
        String kmYn;             //KM 암복호화

        @JsonProperty(value = "file_yn")
        String fileYn;           //FILE 암복호화

        @JsonProperty(value = "hash_yn")
        String hashYn;           //HASH 암복호화

        @JsonProperty(value = "kms_enc")
        String kmsEnc;           //KMS 암호화 및 BA-SCP 연동

        @JsonProperty(value = "kms_pub_key")
        String kmsPubKey;       //SCP 외부키 연동

        @JsonProperty(value = "kms_pri_key")
        String kmsPriKey;       //비공개키 연동

        @JsonProperty(value = "web_cnt")
        Integer webCnt;    // 보호 대상 웹서버 등록수 제한(WAPPLES), -1(무제한)

        @JsonProperty(value = "lis_exp_act")
        Integer lisExpAct;    // 라이선스 만료시 대응 설정(0,1,2)

        @JsonProperty(value = "license_status")
        String licenseStatus;       // 티켓 사용(발급) 여부(0:티켓미사용, 1:티켓사용)
    }

    @Getter
    @Setter
    @NonNull
    public static class ContractFileSummaryInfoDto {
        @JsonProperty(value = "key_idx")
        Integer keyIdx;            //Key ID

        @JsonProperty(value = "contract_file_name")
        String contractFileName;   // 계약 정보 파일명

        @JsonProperty(value = "cust_nm")
        String custNm;             //고객사명

        @JsonProperty(value = "order_num")
        String orderNum;          //납품계약번호

        @JsonProperty(value = "lis_s_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisSDate;       //라이선스 시작일

        @JsonProperty(value = "lis_e_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisEDate;       //라이선스 종료일

        @JsonProperty(value = "ins_s_dt")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date insSDt;         //서비스 시작일

        @JsonProperty(value = "ins_e_dt")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date insEDt;         //서비스 종료일

        @JsonProperty(value = "lis_pro_nm")
        String lisProNm;          //제품명

        @JsonProperty(value = "lis_type_oc_up")
        String lisTypeOcUp;          //라이선스 타입

        @Builder
        public ContractFileSummaryInfoDto(Integer keyIdx, String contractFileName, String custNm, String orderNum,
                                          Date lisSDate, Date lisEDate, Date insSDt, Date insEDt,
                                          String lisProNm, String lisTypeOcUp) {
            this.keyIdx = keyIdx;           //Key ID
            this.contractFileName = contractFileName;
            this.custNm = custNm;           //고객사명
            this.orderNum = orderNum;         //납품계약번호
            this.lisSDate = lisSDate;        //라이선스 시작일
            this.lisEDate = lisEDate;        //라이선스 종료일
            this.insSDt = insSDt;          //서비스 시작일
            this.insEDt = insEDt;          //서비스 종료일
            this.lisProNm = lisProNm;        //제품명
            this.lisTypeOcUp = lisTypeOcUp;    //라이선스 타입
        }
    }

    @Getter
    @Setter
    @NonNull
    public static class ContractCustNameDetailInfoDto {
        @JsonIgnore
        @JsonProperty(value = "key_idx")
        Integer keyIdx;            //Key ID

        @JsonIgnore
        @JsonProperty(value = "contract_file_name")
        String contractFileName;   // 계약 정보 파일명

        @JsonProperty(value = "cust_nm")
        String custNm;             //고객사명

        @JsonIgnore
        @JsonProperty(value = "end_user_id")
        Integer endUserId;         //고객사 번호

        @JsonIgnore
        @JsonProperty(value = "esti_type")
        String estiType;           //계약 타입

        @JsonProperty(value = "order_num")
        String orderNum;          //납품계약번호

        @JsonProperty(value = "lis_s_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisSDate;       //라이선스 시작일

        @JsonProperty(value = "lis_e_date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date lisEDate;       //라이선스 종료일

        @JsonProperty(value = "ins_s_dt")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date insSDt;         //서비스 시작일

        @JsonProperty(value = "ins_e_dt")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date insEDt;         //서비스 종료일

        @JsonIgnore
        @JsonProperty(value = "lis_pro_nm")
        String lisProNm;          //제품명

        @JsonIgnore
        @JsonProperty(value = "cust_ip")
        String custIp;          //IP (라이선스 서버 체크용)

        @JsonIgnore
        @JsonProperty(value = "kms_id")
        String kmsId;          //KMS SA ID

        @JsonIgnore
        @JsonProperty(value = "hw_sn")
        String hwSn;          //제품 ID

        @JsonProperty(value = "lo_core")
        Integer loCore;          //코어 수

        @JsonIgnore
        @JsonProperty(value = "vcore_cnt")
        Integer vcoreCnt;          //KMS SA 코어 수

        @JsonIgnore
        @JsonProperty(value = "lis_ver_up")
        String lisVerUp;          //라이선스 버전

        @JsonIgnore
        @JsonProperty(value = "lis_kind_up")
        String lisKindUp;          //설치 환경

        @JsonProperty(value = "lis_type_oc_up")
        String lisTypeOcUp;          //라이선스 타입

        @JsonIgnore
        @JsonProperty(value = "cloud_sp")
        String cloudSp;          //CSP 종류

        @JsonIgnore
        @JsonProperty(value = "cloud_csi")
        String cloudCsi;          //클라우드 단품

        @JsonIgnore
        @JsonProperty(value = "cloud_sgn")
        String cloudSgn;          //클라우드 Autoscaling

        @JsonIgnore
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

        @JsonIgnore
        @JsonProperty(value = "site_nm")
        String siteNm;          //사이트명

        @JsonIgnore
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

        @Builder
        public ContractCustNameDetailInfoDto(String custNm, String estiType, String orderNum, Date lisSDate, Date lisEDate,
                                             Date insSDt, Date insEDt, Integer loCore, String lisTypeOcUp,
                                             String symApiYn, String pubApiYn, String kewinApiYn, String apiYn,
                                             String kmsYn, String osYn, String kmYn, String fileYn, String hashYn,
                                             String kmsEnc, String kmsPubKey, String kmsPriKey) {
            this.custNm = custNm;           //고객사명
            this.estiType = estiType;       // 계약타입
            this.orderNum = orderNum;         //납품계약번호
            this.lisSDate = lisSDate;        //라이선스 시작일
            this.lisEDate = lisEDate;        //라이선스 종료일
            this.insSDt = insSDt;          //서비스 시작일
            this.insEDt = insEDt;          //서비스 종료일
            this.loCore = loCore;           //코어 수
            this.lisTypeOcUp = lisTypeOcUp;    //라이선스 타입
            this.symApiYn = symApiYn;        //대칭키 암복호화 API
            this.pubApiYn = pubApiYn;        //비대칭키 암복호화 API
            this.kewinApiYn = kewinApiYn;     //KE-WIN 복호화 API
            this.apiYn = apiYn;            //암복호화 API
            this.kmsYn = kmsYn;            //KMS 외부 키 연동
            this.osYn = osYn;             //OS 계정별 암복호화 권한 제어
            this.kmYn = kmYn;             //KM 암복호화
            this.fileYn = fileYn;           //FILE 암복호화
            this.hashYn = hashYn;           //HASH 암복호화
            this.kmsEnc = kmsEnc;           //KMS 암호화 및 BA-SCP 연동
            this.kmsPubKey = kmsPubKey;       //SCP 외부키 연동
            this.kmsPriKey = kmsPriKey;       //비공개키 연동
        }
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
    @NoArgsConstructor
    public static class SaveContractInfoDtoReq {
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
        String lisSDate;       //라이선스 시작일

        @JsonProperty(value = "lis_e_date")
        String lisEDate;       //라이선스 종료일

        @JsonProperty(value = "ins_s_dt")
        String insSDt;         //서비스 시작일

        @JsonProperty(value = "ins_e_dt")
        String insEDt;         //서비스 종료일

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
        String licenseStatus;
    }

    //==================================== Response Class
    @Getter
    @NoArgsConstructor
    public static class ResultDto {
        @JsonProperty(value = "result")
        private Integer result;

        @JsonProperty(value = "msg")
        private String msg;

        @Builder
        public ResultDto(Integer result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PageDto {
        @JsonProperty(value = "pageTotalSize")
        private Long pageTotalSize;

        @Builder
        public PageDto(Long pageTotalSize) {

            this.pageTotalSize = pageTotalSize;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class FindAllPageContractInfoDtoResp {
        ResultDto resultDto;
        PageDto pageDto;
        List<ContractInfoJsonDataDto> findAllContractInfoDtos;
        //List<String> findAllContractInfoDtos;

        @Builder
        public FindAllPageContractInfoDtoResp(ResultDto resultDto, PageDto pageDto,
                                              List<ContractInfoJsonDataDto> findAllContractInfoDtos) {
            this.resultDto = resultDto;
            this.pageDto = pageDto;
            this.findAllContractInfoDtos = findAllContractInfoDtos;
        }
    }

    @Getter
    public static class FindAllContractInfoDtoResp {
        ResultDto resultDto;
        List<ContractInfoJsonDataDto> findAllContractInfoDtos;

        @Builder
        public FindAllContractInfoDtoResp(ResultDto resultDto,
                                          List<ContractInfoJsonDataDto> findAllContractInfoDtos) {
            this.resultDto = resultDto;
            this.findAllContractInfoDtos = findAllContractInfoDtos;
        }
    }

    @Getter
    public static class FindContractInfoDtoResp {
        ResultDto resultDto;
        ContractInfoJsonDataDto findContractInfoDto;

        @Builder
        public FindContractInfoDtoResp(ResultDto resultDto,
                                       ContractInfoJsonDataDto findContractInfoDto) {
            this.resultDto = resultDto;
            this.findContractInfoDto = findContractInfoDto;
        }
    }

    @Getter
    public static class FindAllContractFileGroupInfoDtoResp {
        ResultDto resultDto;
        List<ContractFileGroupInfoDto> ContractFileGroupInfoDtos;

        @Builder
        public FindAllContractFileGroupInfoDtoResp(ResultDto resultDto,
                                                   List<ContractFileGroupInfoDto> ContractFileGroupInfoDtos) {
            this.resultDto = resultDto;
            this.ContractFileGroupInfoDtos = ContractFileGroupInfoDtos;
        }
    }

    @Getter
    public static class FindContractFileSummaryInfoDtoResp {
        ResultDto resultDto;
        PageDto pageDto;
        List<ContractFileSummaryInfoDto> contractFileSummaryInfoDtos;

        @Builder
        public FindContractFileSummaryInfoDtoResp(ResultDto resultDto,
                                                  PageDto pageDto,
                                                  List<ContractFileSummaryInfoDto> contractFileSummaryInfoDtos) {
            this.resultDto = resultDto;
            this.pageDto = pageDto;
            this.contractFileSummaryInfoDtos = contractFileSummaryInfoDtos;
        }
    }

    @Getter
    public static class FindContractCustNameGroupInfoResp {
        ResultDto resultDto;
        List<ContractCustNameGroupInfoDto> contractCustNameGroupInfoDtos;

        @Builder
        public FindContractCustNameGroupInfoResp(ResultDto resultDto,
                                                 List<ContractCustNameGroupInfoDto> contractCustNameGroupInfoDtos) {
            this.resultDto = resultDto;
            this.contractCustNameGroupInfoDtos = contractCustNameGroupInfoDtos;
        }
    }

    @Getter
    public static class FindContractCustNameDetailInfoResp {
        ResultDto resultDto;
        PageDto pageDto;
        List<ContractCustNameDetailInfoDto> contractCustNameDetailInfoDtos;

        @Builder
        public FindContractCustNameDetailInfoResp(ResultDto resultDto, PageDto pageDto,
                                                  List<ContractCustNameDetailInfoDto> contractCustNameDetailInfoDtos) {
            this.resultDto = resultDto;
            this.pageDto = pageDto;
            this.contractCustNameDetailInfoDtos = contractCustNameDetailInfoDtos;
        }
    }
}
