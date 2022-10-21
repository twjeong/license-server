package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillingAmountDetailDto {

    @JsonProperty(value = "year")
    Integer year;

    @JsonProperty(value = "month")
    Integer month;

    @JsonProperty(value = "providedValue")
    Integer providedValue;

    @JsonProperty(value = "tax")
    Integer tax;

    @JsonProperty(value = "amount")
    Integer amount;

    @JsonProperty(value = "lastAmount")
    Integer lastAmount;

    @JsonProperty(value = "variationAmount")
    Integer variationAmount;

    @JsonProperty(value = "productList")
    List<BillingProductDto> productList;

    @Builder
    public BillingAmountDetailDto(
            Integer year,
            Integer month,
            Integer providedValue,
            Integer tax,
            Integer amount,
            Integer lastAmount,
            Integer variationAmount,
            List<BillingProductDto> productList) {

        this.year = year;
        this.month = month;
        this.providedValue = providedValue;
        this.tax = tax;
        this.amount = amount;
        this.lastAmount = lastAmount;
        this.variationAmount = variationAmount;
        this.productList = productList;

    }
}
