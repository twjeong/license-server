package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillingComponentDto {
    @JsonProperty(value = "componentName")
    String componentName;

    @JsonProperty(value = "core")
    Integer core;

    @JsonProperty(value = "calHour")
    Integer calHour;

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

    @Builder
    public BillingComponentDto(
            String componentName,
            Integer core,
            Integer calHour,
            Integer providedValue,
            Integer tax,
            Integer amount,
            Integer lastAmount,
            Integer variationAmount) {

        this.componentName = componentName;
        this.core = core;
        this.calHour = calHour;
        this.providedValue = providedValue;
        this.tax = tax;
        this.amount = amount;
        this.lastAmount = lastAmount;
        this.variationAmount = variationAmount;
    }
}
