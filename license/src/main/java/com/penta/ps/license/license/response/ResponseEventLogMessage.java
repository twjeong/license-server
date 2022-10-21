package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.log.EventLogDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseEventLogMessage {
    List<EventLogDto> eventLogList;
    private int result;
    private String msg;
    private int pageTotalSize;

    @Builder
    public ResponseEventLogMessage(int result,
                                   String msg,
                                   List<EventLogDto> eventLogList,
                                   int pageTotalSize) {
        this.result = result;
        this.msg = msg;
        this.eventLogList = eventLogList;
        this.pageTotalSize = pageTotalSize;
    }
}
