package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.log.LogDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseLog {
    LogDto[] logList;
    List<Integer> pageList;
    private int prevPageNum;
    private int curPageNum;
    private int nextPageNum;
    private int lastPageNum;

    @Builder
    public ResponseLog(LogDto[] logList,
                       List<Integer> pageList,
                       int prevPageNum,
                       int curPageNum,
                       int nextPageNum,
                       int lastPageNum) {
        this.logList = logList;
        this.pageList = pageList;
        this.prevPageNum = prevPageNum;
        this.curPageNum = curPageNum;
        this.nextPageNum = nextPageNum;
        this.lastPageNum = lastPageNum;
    }
}
