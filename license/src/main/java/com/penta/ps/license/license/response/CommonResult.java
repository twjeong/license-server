package com.penta.ps.license.license.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {
    // 응답 코드 번호
    private int result;

    // 응답 메시지
    private String msg;
}
