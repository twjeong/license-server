package com.penta.ps.license.license.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseLogsBackupMessage {

    private int result;
    private String msg;
    private int minute;
    private int hour;
    private int day;
    private String month;
    private boolean enable;
    private String path;

    @Builder
    public ResponseLogsBackupMessage(int result,
                                     String msg,
                                     int minute,
                                     int hour,
                                     int day,
                                     String month,
                                     boolean enable,
                                     String path) {
        this.result = result;
        this.msg = msg;
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.enable = enable;
        this.path = path;
    }
}
