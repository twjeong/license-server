package com.penta.ps.license.license.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {
    private String id;
    private String password;
    private String desc;

    @Builder
    UserInfoDto(String id, String password, String desc) {
        this.id = id;
        this.password = password;
        this.desc = desc;
    }
}
