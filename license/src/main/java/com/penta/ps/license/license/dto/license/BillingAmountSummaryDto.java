package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BillingAmountSummaryDto {

    @JsonProperty(value = "date")
    @JsonFormat(pattern = "yyyy-MM", timezone="Asia/Seoul")
    private Date date;

    @JsonProperty(value = "amount")
    Integer amount;

    @Builder
    public BillingAmountSummaryDto(
            Date date,
            Integer amount
            ) {
        this.date = date;
        this.amount = amount;
    }
}
