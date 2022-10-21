package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.dto.contract.ContractFileGroupInfoDto;
import com.penta.ps.license.license.entity.ContractInfoJsonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractInfoJsonRepository extends JpaRepository<ContractInfoJsonEntity, Integer> {
    public ContractInfoJsonEntity findByKeyIdx(Integer keyIdx);

    public Page<ContractInfoJsonEntity> findByCustNm(Pageable pageable, String custNm);

    public Page<ContractInfoJsonEntity> findByOrderNum(Pageable pageable, String orderNum);

    public Page<ContractInfoJsonEntity> findByLisProNm(Pageable pageable, String lisProNm);

    public Page<ContractInfoJsonEntity> findByContractFileName(Pageable pageable, String contractFileName);

    public Page<ContractInfoJsonEntity> findByCustNmAndOrderNum(Pageable pageable, String custNm, String orderNum);

    public Page<ContractInfoJsonEntity> findByCustNmAndLisProNm(Pageable pageable, String CustName, String coponentType);

    public Page<ContractInfoJsonEntity> findByOrderNumAndLisProNm(Pageable pageable, String orderNum, String lisProNm);

    @Query("SELECT new com.penta.ps.license.license.dto.contract.ContractFileGroupInfoDto( " +
            "s.contractFileName, s.lisProNm, s.lisTypeOcUp, count(s)) " +
            "FROM ContractInfoJsonEntity s " +
            "GROUP BY s.contractFileName, s.lisProNm, s.lisTypeOcUp")
    public List<ContractFileGroupInfoDto> findGroupByContractFileNameAndLisProNmAndLisTypeOcUp();

    public ContractInfoJsonEntity findByContractFileNameAndKeyIdx(String contractFileName, Integer keyIdx);
}
