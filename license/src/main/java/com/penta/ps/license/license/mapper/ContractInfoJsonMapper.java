package com.penta.ps.license.license.mapper;

import com.penta.ps.license.license.dto.contract.ContractInfoJsonDto;
import com.penta.ps.license.license.entity.ContractInfoJsonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractInfoJsonMapper {
    ContractInfoJsonMapper INSTANCE = Mappers.getMapper(ContractInfoJsonMapper.class);

    ContractInfoJsonEntity dtoToEntity(ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto);

    List<ContractInfoJsonEntity> dtosToEntities(List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos);

    ContractInfoJsonDto.InnerContractInfoJsonDto EntityToDto(ContractInfoJsonEntity contractInfoJsonEntity);

    List<ContractInfoJsonDto.InnerContractInfoJsonDto> findAllToDtos(List<ContractInfoJsonEntity> contractInfoJsonEntities);

    List<ContractInfoJsonDto.ContractCustNameDetailInfoDto> findAllCustNmDetailInfoToDtos(List<ContractInfoJsonDto.ContractInfoJsonDataDto> innerContractInfoJsonDtos);

}
