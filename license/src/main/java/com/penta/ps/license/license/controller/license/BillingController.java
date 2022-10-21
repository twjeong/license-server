package com.penta.ps.license.license.controller.license;

import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.license.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;
    private final ResponseService responseService;

    @Transactional
    @GetMapping("/licenses/billing/amount")
    public CommonResult getAmount(
            @RequestParam(value = "year", required = true) int year,
            @RequestParam(value = "month", required = true) int month,
            @RequestParam(value = "custNm", required = true) String custNm
    ) {

        return responseService.getSingleResultWithTotalAmount(
                billingService.getTotalAmount(year, month, custNm)
        );
    }

    @Transactional
    @GetMapping("/licenses/billing/amount-detail")
    public CommonResult getAmountDetail(
            @RequestParam(value = "year", required = true) int year,
            @RequestParam(value = "month", required = true) int month,
            @RequestParam(value = "custNm", required = true) String custNm
    ) {

        return responseService.getSingleResultWithAmountDetail(
                billingService.getAmountDetail(year, month, custNm)
        );
    }

    @Transactional
    @GetMapping("/licenses/billing/amount-half-year")
    public CommonResult getAmountHalfYear(
            @RequestParam(value = "year", required = true) int year,
            @RequestParam(value = "month", required = true) int month,
            @RequestParam(value = "custNm", required = true) String custNm
    ) {

        return responseService.getSingleResultWithAmountHalfYear(
                billingService.getAmountHalfYear(year, month, custNm)
        );
    }

}
