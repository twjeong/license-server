package com.penta.ps.license.license.dto.license;

import com.penta.ps.license.license.type.TypeDefine;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
@NoArgsConstructor
public class StatusOfLicenseInfoDto {
    @Enumerated(value = EnumType.ORDINAL)
    private TypeDefine.Status status;
}
