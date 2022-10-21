package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.penta.ps.license.license.type.ExpireType;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenseInfoDto {
    @JsonAlias({"serialNo", "licenseId"})
    private String serialNo;

    @JsonProperty(value = "nodeId")
    private String nodeId;

    @JsonProperty(value = "keyIdx")
    private Integer keyIdx;

    @JsonProperty(value = "contractFileName")
    private String contractFileName;

    @JsonProperty(value = "custNm")
    private String custNm;

    @JsonAlias({"orderNum", "contractNumber"})
    private String orderNum;

    @Enumerated(value = EnumType.ORDINAL)
    @JsonProperty(value = "licenseType")
    private TypeDefine.LicenseType licenseType;

    @JsonAlias({"componentName", "productName"})
    private String componentName;

    @JsonAlias({"componentVersion", "productVersion"})
    private String componentVersion;

    @JsonAlias({"ipAddress", "productIp"})
    private String ipAddress;

    @JsonAlias({"core", "cpuCount"})
    private int core;

    @JsonProperty(value = "instanceNm")
    private String instanceNm;

    @JsonProperty(value = "licenseFile")
    private String licenseFile;

    @JsonProperty(value = "firstUsageTime")
    private Date firstUsageTime;

    @JsonProperty(value = "lastUsageTime")
    private Date lastUsageTime;

    @JsonProperty(value = "hoursOfUsageTime")
    private int hoursOfUsageTime;

    @JsonProperty(value = "licenseIssueDate")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date licenseIssueDate;

    @JsonProperty(value = "licenseExpiredDate")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date licenseExpiredDate;

    @JsonProperty(value = "licenseStartDate")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date licenseStartDate;

    @JsonProperty(value = "licenseEndDate")
    @JsonFormat(pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private Date licenseEndDate;

    @JsonProperty(value = "status")
    private String status;

    @Enumerated(value = EnumType.ORDINAL)
    @JsonProperty(value = "expireType")
    private ExpireType expireType;

    String symApiYn;        //대칭키 암복호화 API
    String pubApiYn;        //비대칭키 암복호화 API
    String kewinApiYn;     //KE-WIN 복호화 API
    String apiYn;            //암복호화 API
    String kmsYn;            //KMS 외부 키 연동
    String osYn;             //OS 계정별 암복호화 권한 제어
    String lisOsType;       //OS
    String kmYn;             //KM 암복호화
    String fileYn;           //FILE 암복호화
    String hashYn;           //HASH 암복호화
    String kmsEnc;           //KMS 암호화 및 BA-SCP 연동
    String kmsPubKey;       //SCP 외부키 연동
    String kmsPriKey;       //비공개키 연동
    Integer webCnt;         // 보호 대상 웹서버 등록수 제한
    Integer lisExpAct;      // 라이선스 만료시 대응 설정
}
