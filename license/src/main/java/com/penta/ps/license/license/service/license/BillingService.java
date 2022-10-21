package com.penta.ps.license.license.service.license;

import com.penta.ps.license.license.domain.Billing;
import com.penta.ps.license.license.dto.license.BillingAmountDetailDto;
import com.penta.ps.license.license.dto.license.BillingAmountSummaryDto;
import com.penta.ps.license.license.dto.license.BillingComponentDto;
import com.penta.ps.license.license.dto.license.BillingProductDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BillingService implements ApplicationListener<ApplicationReadyEvent> {
    private final Billing billing;

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        billing.generateBillingInfo();
    }

    public Integer getTotalAmount(Integer year, Integer month, String custNm) {

        return billing.generateBillingList(year, month, custNm).stream()
                .mapToInt(Billing::getAmount)
                .sum();
    }

    public BillingAmountDetailDto getAmountDetail(Integer year, Integer month, String custNm) {

        List<Billing> billingList = billing.generateBillingList(year, month, custNm);

        //grouping category
        Map<String, List<Billing>> billingGroup = billingList.stream()
                .collect(groupingBy(Billing::getCategory, toList()));

        List<BillingProductDto> billingProductDtoList = new ArrayList<>();
        billingGroup.forEach((c, l) -> {

            //add BillingComponentDto
            List<BillingComponentDto> billingComponentDtoList = new ArrayList<>();
            l.forEach(o -> {
                billingComponentDtoList.add(
                        BillingComponentDto.builder()
                                .componentName(o.getComponentName())
                                .core(o.getCore())
                                .calHour(o.getUsageHour())
                                .providedValue(o.getProvidedValue())
                                .tax(o.getTax())
                                .amount(o.getAmount())
                                .lastAmount(o.getLastAmount())
                                .variationAmount(o.getVariationAmount())
                                .build());
            });

            //add BillingProductDto
            billingProductDtoList.add(
                    BillingProductDto.builder()
                            .category(c)
                            .providedValue(l.stream().mapToInt(Billing::getProvidedValue).sum())
                            .tax(l.stream().mapToInt(Billing::getTax).sum())
                            .amount(l.stream().mapToInt(Billing::getAmount).sum())
                            .lastAmount(l.stream().mapToInt(Billing::getLastAmount).sum())
                            .variationAmount(l.stream().mapToInt(Billing::getVariationAmount).sum())
                            .componentList(billingComponentDtoList)
                            .build());
        });

        return BillingAmountDetailDto.builder()
                .year(year)
                .month(month)
                .providedValue(billingProductDtoList.stream()
                        .mapToInt(BillingProductDto::getProvidedValue)
                        .sum())
                .tax(billingProductDtoList.stream()
                        .mapToInt(BillingProductDto::getTax)
                        .sum())
                .amount(billingProductDtoList.stream()
                        .mapToInt(BillingProductDto::getAmount)
                        .sum())
                .lastAmount(billingProductDtoList.stream()
                        .mapToInt(BillingProductDto::getLastAmount)
                        .sum())
                .variationAmount(billingProductDtoList.stream()
                        .mapToInt(BillingProductDto::getVariationAmount)
                        .sum())
                .productList(billingProductDtoList)
                .build();
    }

    public List<BillingAmountSummaryDto> getAmountHalfYear(int year, int month, String custNm) {

        final int numOfMonth = 6;
        final YearMonth current = YearMonth.of(year, month);

        List<BillingAmountSummaryDto> billingAmountSummaryDtoList = new ArrayList<>();

        for (int i = 0; i < numOfMonth; i++) {

            YearMonth last = current.minusMonths((numOfMonth - 1) - i);
            billingAmountSummaryDtoList.add(
                    BillingAmountSummaryDto.builder()
                            .date(Date.from(last.atEndOfMonth().atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant()))
                            .amount(getTotalAmount(last.getYear(), last.getMonthValue(), custNm))
                            .build());
        }

        return billingAmountSummaryDtoList;
    }
}
