package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseRegisterDto {
    String serialNo;        // 시리얼번호

    @Temporal(TemporalType.TIMESTAMP)
    private Date firstUsageTime;

    @JsonProperty(value = "licenseIssueDate")
    @JsonFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    private Date licenseIssueDate;

    @JsonFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    private Date licenseStartDate;

    @JsonFormat(pattern = "yyyyMMdd")
    @Temporal(TemporalType.DATE)
    private Date licenseEndDate;

    TypeDefine.Status status;    // 라이선스 상태
    String custNm;             //고객사명
    Integer endUserId;         //고객사 번호
    String estiType;           //계약 타입
    String orderNum;          //납품계약번호
    LocalDate startDate;       //라이선스 시작일
    LocalDate endDate;       //라이선스 종료일
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
    String lisOsType;          //OS
    String kmYn;          //KM 암복호화
    String fileYn;          //FILE 암복호화
    String hashYn;          //HASH 암복호화
    String kmsEnc;          //KMS 암호화 및 BA-SCP 연동
    String kmsPubKey;          //SCP 외부키 연동
    String kmsPriKey;          //비공개키 연동
}
