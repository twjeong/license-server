package com.penta.ps.license.license.dto.license;

import lombok.*;

public class CommonLicenseStateDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommonLicenseWapplesDtoResp {
        String licenseStatus;              // license 발급상태, node-id(UUID) 불일치시 duplicated로 반환
        String licenseType;         // 라이선스 타입
        Boolean shouldRenewCert;    // 라이선스 갱신 필요 여부 반환, 발급된 인증서의 유효기간이 15일 이하일 경우 true 그외 false
    }
}
