package com.penta.ps.license.license.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResultWithPageTotalSize<T> {
    private List<T> licenseList;

    private int result;

    private String msg;

    private int pageTotalSize;
}
