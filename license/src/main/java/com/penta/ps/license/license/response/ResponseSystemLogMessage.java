package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.log.SystemLogDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseSystemLogMessage {
    List<SystemLogDto> systemLogList;
    private int result;
    private String msg;
    private int pageTotalSize;

    @Builder
    public ResponseSystemLogMessage(int result,
                                    String msg,
                                    List<SystemLogDto> systemLogList,
                                    int pageTotalSize) {
        this.result = result;
        this.msg = msg;
        this.systemLogList = systemLogList;
        this.pageTotalSize = pageTotalSize;
    }
}
