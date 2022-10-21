package com.penta.ps.license.license.repository;

import com.penta.ps.license.license.entity.BillingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillingInfoRepository extends JpaRepository<BillingInfoEntity, Long> {

    List<BillingInfoEntity> findByComponentName(String componentName);

    Optional<BillingInfoEntity> findByComponentNameAndCore(String componentName, int core);

}
