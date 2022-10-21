package com.penta.ps.license.license.dto.common;

import com.penta.ps.license.license.type.ManagerType;
import com.penta.ps.license.license.type.LogLevelType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogModuleDto {

    @Getter
    @NoArgsConstructor
    public static class EventLogReqDto {
        private ManagerType facility;
        private LogLevelType logLevel;
        private String msg;

        @Builder
        public EventLogReqDto(ManagerType facility, LogLevelType logLevel, String msg) {
            if (facility.equals(null)) {
                facility = ManagerType.LICENSE;
            }

            if (logLevel.equals(null)) {
                logLevel = LogLevelType.INFO;
            }

            this.facility = facility;
            this.logLevel = logLevel;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PolicyLogReqDto {
        private ManagerType facility;
        private LogLevelType logLevel;
        private String msg;

        @Builder
        public PolicyLogReqDto(ManagerType facility, LogLevelType logLevel, String msg) {
            if (facility.equals(null)) {
                facility = ManagerType.LICENSE;
            }

            if (logLevel.equals(null)) {
                logLevel = LogLevelType.INFO;
            }

            this.facility = facility;
            this.logLevel = logLevel;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SystemLogReqDto {
        private ManagerType facility;
        private LogLevelType logLevel;
        private String msg;

        @Builder
        public SystemLogReqDto(ManagerType facility, LogLevelType logLevel, String msg) {
            if (facility.equals(null)) {
                facility = ManagerType.LICENSE;
            }

            if (logLevel.equals(null)) {
                logLevel = LogLevelType.INFO;
            }

            this.facility = facility;
            this.logLevel = logLevel;
            this.msg = msg;
        }
    }


    @Getter
    @NoArgsConstructor
    public static class EventLogResDto {
        private Integer result;
        private String msg;

        @Builder
        public EventLogResDto(Integer result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class SystemLogResDto {
        private Integer result;
        private String msg;

        @Builder
        public SystemLogResDto(Integer result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PolicyLogResDto {
        private Integer result;
        private String msg;

        @Builder
        public PolicyLogResDto(Integer result, String msg) {
            this.result = result;
            this.msg = msg;
        }
    }
}
