package com.penta.ps.license.license.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleResultWithStatusNLicenseType<T> extends CommonResult {
    private T status;
    private T licenseType;
}
