package com.penta.ps.license.license.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contract_info_json", schema = "contract", catalog = "contract")
public class ContractInfoJsonEntity {
    @Id
    @Column(name = "key_idx", columnDefinition = "int", updatable = false, nullable = false)
    Integer keyIdx;           //Key ID

    @Column(name = "contract_file_name", length = 128)
    String contractFileName;  // 계약정보 파일명

    @Column(length = 128, name = "cust_nm")
    String custNm;           //고객사명

    @Column(length = 64, name = "order_num")
    String orderNum;         //납품계약번호

    @Column(length = 16, name = "lis_pro_nm")
    String lisProNm;        //제품명

    @Column(length = 32, name = "lis_type_oc_up")
    String lisTypeOcUp;    //라이선스 타입

    @Column(name = "core_count", columnDefinition = "smallint")
    Integer coreCount;           //코어 수

    @Column(name = "lis_start_date")
    Date lisStartDate;        //라이선스 시작일

    @Column(name = "lis_end_date")
    Date lisEndDate;        //라이선스 종료일

    @Column(length = 1, name = "license_status", columnDefinition = "char")
    String licenseStatus;       // 티켓 사용(발급) 여부(0:티켓미사용, 1:티켓사용)

    @Column(name = "contract_info_json", columnDefinition = "json")
    String contractInfoJson;  // 계약정보 json

    @Builder
    public ContractInfoJsonEntity(Integer keyIdx, String contractFileName,
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
