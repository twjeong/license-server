package com.penta.ps.license.license.dto.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogsBackupDto {

    @JsonProperty(value = "minute")
    private Integer minute;

    @JsonProperty(value = "hour")
    private Integer hour;

    @JsonProperty(value = "day")
    private Integer day;

    @JsonProperty(value = "month")
    private String month;

    @JsonProperty(value = "enable")
    private Boolean enable;

    @JsonProperty(value = "force")
    private Boolean force;

    @JsonProperty(value = "path")
    private String path;

    @Builder
    public LogsBackupDto(Integer minute,
                         Integer hour,
                         Integer day,
                         String month,
                         Boolean enable,
                         Boolean force,
                         String path) {
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.enable = enable;
        this.force = force;
        this.path = path;
    }
}
