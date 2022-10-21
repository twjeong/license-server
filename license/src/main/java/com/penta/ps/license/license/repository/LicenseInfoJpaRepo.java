package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.dto.license.ContractServiceInfoDto;
import com.penta.ps.license.license.dto.license.ContractTicketCountInfoDto;
import com.penta.ps.license.license.dto.license.ServiceCountDto;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.type.TypeDefine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LicenseInfoJpaRepo extends JpaRepository<LicenseInfo, String> {

    Optional<LicenseInfo> findBySerialNo(String serialNo);

    //LicenseInfo findBySerialNo(String serialNo);

    List<LicenseInfo> findByNodeId(String nodeId);

    List<LicenseInfo> findByComponentName(String componentName);

    Page<LicenseInfo> findBySerialNoContainingIgnoreCase(String serialNo, Pageable pageable);

    Page<LicenseInfo> findByLicenseType(TypeDefine.LicenseType licenseType, Pageable pageable);

    Page<LicenseInfo> findByComponentNameContainingIgnoreCase(String componentName, Pageable pageable);

    Page<LicenseInfo> findByComponentVersionContainingIgnoreCase(String componentVersion, Pageable pageable);

    Page<LicenseInfo> findByIpAddressContainingIgnoreCase(String ipAddress, Pageable pageable);

    Page<LicenseInfo> findByLicenseTypeOrSerialNoContainingOrComponentNameContainingOrComponentVersionContainingOrIpAddressContaining(
            TypeDefine.LicenseType licenseType,
            String serialNo,
            String componentName,
            String componentVersion,
            String ipAddress,
            Pageable pageable);

    LicenseInfo findByContractFileNameAndKeyIdxAndStatusIn(String contractFileName, Integer keyIdx, List<TypeDefine.Status> statusList);
    LicenseInfo findByKeyIdxAndStatusIn(Integer keyIdx, List<TypeDefine.Status> statusList);

    @Query(value = "SELECT new com.penta.ps.license.license.dto.license.ServiceCountDto(" +
            "sum(case when l.status = :status " +
            "and l.contractFileName = :contractFileName " +
            "and l.licenseType = :licenseType then 1 else 0 end), " +
            "sum(case when l.status = :status " +
            "and l.contractFileName = :contractFileName " +
            "and l.licenseType <> :licenseType then 1 else 0 end)) " +
            "FROM LicenseInfo l "
    )
    public List<ServiceCountDto> getServiceCount(@Param("status") TypeDefine.Status status,
                                                 @Param("contractFileName") String contractFileName,
                                                 @Param("licenseType") TypeDefine.LicenseType licenseType);

    @Query(value = "select new com.penta.ps.license.license.dto.license.ContractServiceInfoDto(" +
            "l.custNm," +
            "l.licenseType," +
            "l.componentName," +
            "count(l)," +
            "sum(case when l.status = :status then 1 else 0 end)," +
            "sum(l.hoursOfUsageTime)) " +
            "from LicenseInfo l " +
            "where l.custNm = :customer " +
            "group by l.custNm, l.licenseType, l.componentName"
    )
    public List<ContractServiceInfoDto> getContractServiceInfo(@Param("status") TypeDefine.Status status, @Param("customer") String customer);

    /*
    public List<LicenseInfo> findByStatusInAndLicenseStartDateLessThanEqualAndLicenseEndDateGreaterThanEqualAndLicenseTypeIn(
            Collection<TypeDefine.Status> status, Date licenseStartDate, Date licenseEndDate, Collection<TypeDefine.LicenseType> licenseType);
   */

    @Query(value = "select new com.penta.ps.license.license.dto.license.ContractTicketCountInfoDto(s.contractFileName, s.custNm, s.orderNum, s.componentName, s.licenseType, 0L, 0L, 0L, count(s) )" +
            "from LicenseInfo s " +
            "where s.status in (:status1, :status2) " +
            "and s.licenseStartDate <= function('date_format', function('now') , '%Y%m%d') " +
            "and s.licenseEndDate >= function('date_format', function('now') , '%Y%m%d') " +
            "and s.licenseType in (:licenseType1, :licenseType2) " +
            "group by s.contractFileName, s.custNm, s.orderNum, s.componentName, s.licenseType")
    public List<ContractTicketCountInfoDto> findContractTicketCountInfo(@Param("status1") TypeDefine.Status status1,
                                                                        @Param("status2") TypeDefine.Status status2,
                                                                        @Param("licenseType1") TypeDefine.LicenseType licenseType1,
                                                                        @Param("licenseType2") TypeDefine.LicenseType licenseType2);

    List<LicenseInfo> findBySerialNoIn(List<String> serialNos);
}