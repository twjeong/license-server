package com.penta.ps.license.license.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResultWithAmountDetail<T> extends CommonResult {
    private T amountList;
}
