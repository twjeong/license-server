package com.penta.ps.license.license.response;


import com.penta.ps.license.license.dto.log.LogsRestoreDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class ResponseLogsRestoreMessage {

    List<LogsRestoreDto> fileList;
    private int result;
    private String msg;
}
