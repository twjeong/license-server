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
public class BillingProductDto {
    @JsonProperty(value = "category")
    String category;

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

    @JsonProperty(value = "componentList")
    List<BillingComponentDto> componentList;

    @Builder
    public BillingProductDto(
            String category,
            Integer providedValue,
            Integer tax,
            Integer amount,
            Integer lastAmount,
            Integer variationAmount,
            List<BillingComponentDto> componentList) {

        this.category = category;
        this.providedValue = providedValue;
        this.tax = tax;
        this.category = category;
        this.amount = amount;
        this.lastAmount = lastAmount;
        this.variationAmount = variationAmount;
        this.componentList = componentList;
    }
}
