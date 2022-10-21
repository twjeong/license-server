package com.penta.ps.license.license.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleResultWithServiceCountInUse<T> extends CommonResult {
    private T serviceCountInUse;
}
