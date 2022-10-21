package com.penta.ps.license.license.dto.license;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUsageTimeDtoReq {
    @JsonAlias({"serialNo", "licenseId"})
    private String serialNo;

    @JsonProperty(value = "updateDate")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate date;

    @JsonProperty(value = "custNm")
    private String custNm;

    @JsonProperty(value = "minuteOfUsageTime")
    private Integer minuteOfUsageTime;

    @JsonProperty(value = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @JsonProperty(value = "modifyTime")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime modifyTime;
}
