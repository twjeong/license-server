package com.penta.ps.license.license.dto.monitor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsageTimeInfoDto {

    //==================================== Request Class
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UsageTimeDtoReq {
        private Integer currentPageNo;
        private Integer recordsPerPage;
        private LocalDate startDate;
        private LocalDate endDate;
        private String serialNo;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class SaveUsageTimeDtoReq {
        @JsonProperty(value = "serialNo")
        private String serialNo;

        @JsonProperty(value = "saveDate")
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

        @Builder
        public SaveUsageTimeDtoReq(String serialNo, LocalDate date, String custNm, Integer minuteOfUsageTime,
                                   LocalDateTime createTime, LocalDateTime modifyTime) {
            this.serialNo = serialNo;
            this.date = date;
            this.custNm = custNm;
            this.minuteOfUsageTime = minuteOfUsageTime;
            this.createTime = createTime;
            this.modifyTime = modifyTime;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UpdateUsageTimeDtoReq {
        @JsonProperty(value = "serialNo")
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

        @Builder
        public UpdateUsageTimeDtoReq(String serialNo, LocalDate date, String custNm, Integer minuteOfUsageTime,
                                     LocalDateTime createTime, LocalDateTime modifyTime) {
            this.serialNo = serialNo;
            this.date = date;
            this.custNm = custNm;
            this.minuteOfUsageTime = minuteOfUsageTime;
            this.createTime = createTime;
            this.modifyTime = modifyTime;
        }
    }

    //==================================== Response Class
    @Getter
    @Builder
    @NoArgsConstructor
    public static class CommonDtoResp {
        @JsonProperty(value = "result")
        private Integer result;

        @JsonProperty(value = "msg")
        private String msg;

        @Builder
        public CommonDtoResp(Integer result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PageDtoResp {
        @JsonProperty(value = "pageTotalSize")
        private Long pageTotalSize;

        @Builder
        public PageDtoResp(Long pageTotalSize) {

            this.pageTotalSize = pageTotalSize;
        }
    }

    @Getter
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UsageTimeInfoDtoResp {
        @JsonProperty(value = "serialNo")
        private String serialNo;

        @JsonProperty(value = "date")
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

        @Builder
        public UsageTimeInfoDtoResp(String serialNo, LocalDate date, String custNm, Integer minuteOfUsageTime,
                                    LocalDateTime createTime, LocalDateTime modifyTime) {
            this.serialNo = serialNo;
            this.date = date;
            this.custNm = custNm;
            this.minuteOfUsageTime = minuteOfUsageTime;
            this.createTime = createTime;
            this.modifyTime = modifyTime;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ResponseDto {
        private CommonDtoResp commonDtoResp;

        @JsonIgnore
        private PageDtoResp pageDtoResp;

        @JsonIgnore
        private List<UsageTimeInfoDtoResp> usageTimeInfoDtoResp;

        @Builder
        public ResponseDto(CommonDtoResp commonDtoResp, PageDtoResp pageDtoResp, List<UsageTimeInfoDtoResp> usageTimeInfoDtoResp) {
            this.commonDtoResp = commonDtoResp;
            this.pageDtoResp = pageDtoResp;
            this.usageTimeInfoDtoResp = usageTimeInfoDtoResp;
        }

        @Builder
        public ResponseDto(CommonDtoResp commonDtoResp, List<UsageTimeInfoDtoResp> usageTimeInfoDtoResp) {
            this.commonDtoResp = commonDtoResp;
            this.usageTimeInfoDtoResp = usageTimeInfoDtoResp;
        }

    }
}
