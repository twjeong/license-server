package com.penta.ps.license.license.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResponseMessage {
    private int result;
    private String msg;
}
