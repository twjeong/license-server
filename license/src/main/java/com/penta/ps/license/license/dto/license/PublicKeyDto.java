package com.penta.ps.license.license.dto.license;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicKeyDto {
    private String publicKey;
    private String msg;
    private int result;
}
