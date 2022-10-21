package com.penta.ps.license.license.dto.license;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ServiceCountDto {
    Long ticketCountInUse;
    Long cloudCountInUse;
}
