package com.penta.ps.license.license.dto.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LogsRestoreDto {

    @JsonProperty(value = "fileName")
    private String fileName;

    @Builder
    public LogsRestoreDto(String fileName) {
        this.fileName = fileName;
    }
}
