package com.penta.ps.license.license.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoChangePasswordDto {
    private String id;
    private String currentPassword;
    private String newPassword;

    @Builder
    public UserInfoChangePasswordDto(String id, String currentPassword, String newPassword) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
