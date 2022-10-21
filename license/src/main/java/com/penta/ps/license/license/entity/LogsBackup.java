package com.penta.ps.license.license.entity;

import com.penta.ps.license.license.dto.log.LogsBackupDto;
import com.penta.ps.license.license.dto.log.LogsRestoreDto;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ini4j.Ini;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class LogsBackup {

    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbUserPassword;
    @Value("${storage-ip}")
    private String storageIp;
    @Value("${storage-port}")
    private String storagePort;

    @Value("${backup.parent-dir}")
    private String backupParentDir;
    @Value("${backup.minute}")
    private String backupMinute;
    @Value("${backup.hour}")
    private String backupHour;
    @Value("${backup.day}")
    private String backupDay;
    @Value("${backup.month}")
    private String backupMonth;
    @Value("${backup.enable}")
    private String backupEnable;

    private Ini ini = new Ini();

    private ApplicationHome home = new ApplicationHome(this.getClass());

    public void backup() throws IOException, InterruptedException {

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        String fileName = "backup_logs_" + zdt.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".sql";
        File dir = new File(home.getDir(), backupParentDir);
        dir.mkdir();
        String cmd = "mysqldump "
                + "--host=" + storageIp + " "
                + "--port=" + storagePort + " "
                + "-u" + dbUserName + " "
                + "-p" + dbUserPassword + " "
//                + "--lock-tables=0" + " "
//                + "--skip-lock-tables" + " "
//                + "--all-databases "
                + "--skip-add-drop-table "
                + "--no-create-info "
                + "--databases log "
                + "-r" + dir.getPath() + File.separator + fileName;
        log.info("cmd : " + cmd);

        Process runtimeProcess = Runtime.getRuntime().exec(cmd);
        runtimeProcess.waitFor();
    }

    public void restore(String fileName) throws IOException, InterruptedException {
        String filePath = home.getDir() + File.separator +
                backupParentDir + File.separator +
                fileName;
/*
        String cmd =
                "/bin/ash -c \"mysql "
                        + "--host=" + storageIp + " "
                        + "--port=" + storagePort + " "
                        + "-u" + dbUserName + " "
                        + "-p" + dbUserPassword + " "
                        + "--database=log" + " "
                        + "< "
                        + fileName
                        + "\"";
*/
        String[] cmd = {
                "/bin/sh",
                "-c",
                "mysql " + "--host=" + storageIp + " "
                        + "--port=" + storagePort + " "
                        + "-u " + dbUserName + " "
                        + "-p" + dbUserPassword + " "
                        + "log" + " "
                        + "< "
                        + filePath
        };
        log.info("cmd : " + cmd);
        Process runtimeProcess = Runtime.getRuntime().exec(cmd);
        runtimeProcess.waitFor();
    }

    public LogsBackupDto load() throws IOException {

        File dir = new File(home.getDir(), "conf");
        if (!dir.exists()) {
            dir.mkdir();
        }

        ini.clear();

        File file = new File(dir, "backup.ini");
        if (!file.exists()) {
            if (file.createNewFile()) {
                ini.put("schedule", "minute", backupMinute);
                ini.put("schedule", "hour", backupHour);
                ini.put("schedule", "day", backupDay);
                ini.put("schedule", "month", backupMonth);
                ini.put("schedule", "enable", backupEnable);
                ini.store(file);
            }
        } else {
            ini.load(file);
        }

        String path = home.getDir() + File.separator + backupParentDir;
        if (home.getDir().toString().contentEquals("/")) {
            path = "./docker/webapp/backup";
        }

        return LogsBackupDto.builder()
                .minute(Integer.parseInt(0 + ini.get("schedule", "minute")))
                .hour(Integer.parseInt(0 + ini.get("schedule", "hour")))
                .day(Integer.parseInt(0 + ini.get("schedule", "day")))
                .month(ini.get("schedule", "month"))
                .enable(Boolean.parseBoolean(ini.get("schedule", "enable")))
                .force(Boolean.parseBoolean(ini.get("schedule", "force")))
                .path(path)
                .build();
    }

    public void save(LogsBackupDto logsBackupDto) throws IOException {

        File dir = new File(home.getDir(), "conf");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(dir, "backup.ini");
        if (!file.exists()) {
            file.createNewFile();
        }

        ini.clear();

        ini.put("schedule", "minute", logsBackupDto.getMinute().toString());
        ini.put("schedule", "hour", logsBackupDto.getHour().toString());
        ini.put("schedule", "day", logsBackupDto.getDay().toString());
        ini.put("schedule", "month", logsBackupDto.getMonth());
        ini.put("schedule", "enable", logsBackupDto.getEnable().toString());
        ini.store(file);
    }

    public List<LogsRestoreDto> backupFileList() {
        List<LogsRestoreDto> fileNameList = new ArrayList<>();

        File dir = new File(home.getDir(), backupParentDir);

        File[] files = dir.listFiles(o -> o.getName().endsWith(".sql"));

        if (files != null) {
            for (File file : files) {
                fileNameList.add(LogsRestoreDto.builder()
                        .fileName(file.getName())
                        .build());
            }
        }

        return fileNameList;
    }
}
