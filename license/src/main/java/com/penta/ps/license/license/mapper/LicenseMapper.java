package com.penta.ps.license.license.mapper;

import com.penta.ps.license.license.dto.license.LicenseInfoDto;
import com.penta.ps.license.license.entity.LicenseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LicenseMapper {
    LicenseMapper INSTANCE = Mappers.getMapper(LicenseMapper.class);

    public LicenseInfo licenseInfoDtoToEntity(LicenseInfoDto licenseInfoDto);

    public LicenseInfoDto entityToLicenseInfoDto(LicenseInfo licenseInfo);

    public List<LicenseInfoDto> entitiesToLicenseInfoDtos(List<LicenseInfo> licenseInfos);

    public LicenseInfo dtoToEntity(LicenseInfoDto licenseInfoDto);
}
