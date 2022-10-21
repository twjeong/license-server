package com.penta.ps.license.license.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.penta.ps.license.license.service.license.BillingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.net.ssl.SSLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillingServiceTest {

    @Autowired
    private BillingService billingService;

    @Test
    public void getAmountTest() throws SSLException, JsonProcessingException {
        billingService.getTotalAmount(2021, 6, "65.25");
    }

    @Test
    public void getAmountDetailTest() throws SSLException, JsonProcessingException {
        billingService.getAmountDetail(2021, 6, "65.25");
    }

    @Test
    public void getAmountHalfYearTest() throws SSLException, JsonProcessingException {
        billingService.getAmountHalfYear(2021, 6, "LSN2");
    }

}
