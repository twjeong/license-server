package com.penta.ps.license.license.mapper;

import com.penta.ps.license.license.dto.mail.EmailInfoDto;
import com.penta.ps.license.license.entity.EmailInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmailMapper {
    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    public EmailInfo emailDtoToEntity(EmailInfoDto emailInfoDto);

    public EmailInfoDto entityToEmailIDto(EmailInfo emailInfo);
}
