package com.penta.ps.license.license.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResultWithStatus<T> extends CommonResult {
    private T status;
}