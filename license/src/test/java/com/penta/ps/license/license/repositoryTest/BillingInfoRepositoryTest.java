package com.penta.ps.license.license.repositoryTest;

import com.penta.ps.license.license.domain.Billing;
import com.penta.ps.license.license.entity.BillingInfoEntity;
import com.penta.ps.license.license.repository.BillingInfoRepository;
import com.penta.ps.license.license.type.TypeDefine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingInfoRepositoryTest {

    @Autowired
    BillingInfoRepository billingInfoRepository;

    @Autowired
    Billing billing;

    @Test
    public void load() {
        //given
        String componentName = "BA-SCP";

        billingInfoRepository.save(BillingInfoEntity.builder()
                        .componentName(componentName)
                        .billingType(TypeDefine.BillingType.INSTANCE)
                        .core(0)
                        .hourlyPrice(600)
                        .basePrice(0)
                        .build());

        //when
        List<BillingInfoEntity> billingInfoEntities = billingInfoRepository.findAll();

        //then
        BillingInfoEntity billingInfoEntity = billingInfoEntities.get(0);
        assertThat(billingInfoEntity.getComponentName()).isEqualTo(componentName);
    }

    @Test
    public void save() {

        List<BillingInfoEntity> l = new ArrayList<>();

        l.add(new BillingInfoEntity("BA-SCP", TypeDefine.BillingType.INSTANCE, 0, 700, 0));
        l.add(new BillingInfoEntity("KE-WIN", TypeDefine.BillingType.BASE, 2, 100, 500));
        l.add(new BillingInfoEntity("DE-MYQ", TypeDefine.BillingType.BASE, 2, 100, 500));

        billingInfoRepository.saveAll(l);

    }
}
