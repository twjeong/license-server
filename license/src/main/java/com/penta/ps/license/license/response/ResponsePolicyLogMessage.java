package com.penta.ps.license.license.response;

import com.penta.ps.license.license.dto.log.PolicyLogDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponsePolicyLogMessage {
    List<PolicyLogDto> policyLogList;
    private int result;
    private String msg;
    private int pageTotalSize;

    @Builder
    public ResponsePolicyLogMessage(int result,
                                    String msg,
                                    List<PolicyLogDto> policyLogList,
                                    int pageTotalSize) {
        this.result = result;
        this.msg = msg;
        this.policyLogList = policyLogList;
        this.pageTotalSize = pageTotalSize;
    }
}
