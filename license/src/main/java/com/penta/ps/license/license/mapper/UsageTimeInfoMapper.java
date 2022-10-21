package com.penta.ps.license.license.mapper;

import com.penta.ps.license.license.dto.monitor.*;
import com.penta.ps.license.license.entity.UsageTimeInfoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsageTimeInfoMapper {
    UsageTimeInfoMapper INSTANCE = Mappers.getMapper(UsageTimeInfoMapper.class);

    UsageTimeInfoDto.UsageTimeInfoDtoResp toDto(UsageTimeInfoEntity usageTimeInfoEntity);

    List<UsageTimeInfoDto.UsageTimeInfoDtoResp> toDtos(List<UsageTimeInfoEntity> usageTimeInfoEntities);

    List<UsageTimeInfoDto.UsageTimeInfoDtoResp> dtosToDtos(List<UsageTimeInfoCustNameGroupInfoDto> usageTimeInfoCustNameGroupInfoDtos);

    List<UsageTimeInfoDto.UsageTimeInfoDtoResp> dailySumarryDtosToTimeInfoDtos(List<DailySummaryInfoDto> dailySummaryInfoDtos);

    List<UsageTimeInfoDto.UsageTimeInfoDtoResp> allDateSumarryDtosToTimeInfoDtos(List<AllDateSummaryInfoDto> allDateSummaryInfoDto);

    UsageTimeInfoEntity toEntity(UsageTimeInfoDto.SaveUsageTimeDtoReq saveUsageTimeDtoReq);

    UsageTimeInfoEntity toEntity(UsageTimeInfoDto.UpdateUsageTimeDtoReq updateUsageTimeDtoReq);

    List<UsageTimeInfoDto.UsageTimeInfoDtoResp> usageTimeSerialNoGroupDtosToTimeInfoDtos(List<UsageTimeSerialNoGroupDto> usageTimeSerialNoGroupDtos);
}
