package com.penta.ps.license.license.entity;

import com.penta.ps.license.license.type.TypeDefine;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "license_info", schema = "license", catalog = "license")
public class LicenseInfo {
    @Id
    @Column(name = "serial_no", nullable = false, length = 128)
    private String serialNo;

    @Column(name = "node_id", nullable = false, length = 128)
    private String nodeId;

    @Column(name = "key_idx", columnDefinition = "int")
    private Integer keyIdx;

    @Column(name = "contract_file_name", length = 128)
    private String contractFileName;

    @Column(name = "cust_nm", length = 128)
    private String custNm;

    @Column(name = "order_num", length = 64)
    private String orderNum;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "license_type", columnDefinition = "smallint")
    private TypeDefine.LicenseType licenseType;

    @Column(name = "component_name", length = 64)
    private String componentName;

    @Column(name = "component_version", length = 64)
    private String componentVersion;

    @Column(name = "ip_address", columnDefinition = "char", length = 48)
    private String ipAddress;

    @Column(name = "core", columnDefinition = "smallint")
    private int core;

    @Column(name = "instance_nm", length = 128)
    private String instanceNm;

    @Column(name = "license_file", length = 1024)
    private String licenseFile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "first_usage_time")
    private Date firstUsageTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_usage_time")
    private Date lastUsageTime;

    @Column(name = "hours_of_usage_time", columnDefinition = "int")
    private int hoursOfUsageTime;

    @Temporal(TemporalType.DATE)
    @Column(name = "license_issue_date")
    private Date licenseIssueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "license_expired_date")
    private Date licenseExpiredDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "license_start_date")
    private Date licenseStartDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "license_end_date")
    private Date licenseEndDate;

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "status", columnDefinition = "tinyint")
    private TypeDefine.Status status;

    @Column(length = 1, name = "sym_api_yn", columnDefinition = "char")
    String symApiYn;        //대칭키 암복호화 API

    @Column(length = 1, name = "pub_api_yn", columnDefinition = "char")
    String pubApiYn;        //비대칭키 암복호화 API

    @Column(length = 1, name = "kewin_api_yn", columnDefinition = "char")
    String kewinApiYn;     //KE-WIN 복호화 API

    @Column(length = 1, name = "api_yn", columnDefinition = "char")
    String apiYn;            //암복호화 API

    @Column(length = 1, name = "kms_yn", columnDefinition = "char")
    String kmsYn;            //KMS 외부 키 연동

    @Column(length = 1, name = "os_yn", columnDefinition = "char")
    String osYn;             //OS 계정별 암복호화 권한 제어

    @Column(length = 16, name = "lis_os_type")
    String lisOsType;       //OS

    @Column(length = 1, name = "km_yn", columnDefinition = "char")
    String kmYn;             //KM 암복호화

    @Column(length = 1, name = "file_yn", columnDefinition = "char")
    String fileYn;           //FILE 암복호화

    @Column(length = 1, name = "hash_yn", columnDefinition = "char")
    String hashYn;           //HASH 암복호화

    @Column(length = 1, name = "kms_enc", columnDefinition = "char")
    String kmsEnc;           //KMS 암호화 및 BA-SCP 연동

    @Column(length = 1, name = "kms_pub_key", columnDefinition = "char")
    String kmsPubKey;       //SCP 외부키 연동

    @Column(length = 1, name = "kms_pri_key", columnDefinition = "char")
    String kmsPriKey;       //비공개키 연동

    @Column(name = "web_cnt", columnDefinition = "int")
    private Integer webCnt;

    @Column(name = "lis_exp_act", columnDefinition = "int")
    private Integer lisExpAct;
}
