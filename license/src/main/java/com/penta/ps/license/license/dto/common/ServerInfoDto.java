package com.penta.ps.license.license.dto.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfoDto {
    double usageCpu;
    double totalMemory;
    double freeMemory;
    double usageMemory;
    double totalSpace;
    double usageSpace;

    @Builder
    public ServerInfoDto(double usageCpu,
                         double totalMemory,
                         double freeMemory,
                         double usageMemory,
                         double totalSpace,
                         double usageSpace) {
        this.usageCpu = Math.floor(usageCpu);
        this.totalMemory = Math.floor(totalMemory);
        this.freeMemory = Math.floor(freeMemory);
        this.usageMemory = Math.floor(usageMemory);
        this.totalSpace = totalSpace;
        this.usageSpace = usageSpace;
    }
}
