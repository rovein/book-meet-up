package ua.nure.bookmeetup.service.impl;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ua.nure.bookmeetup.service.AdminService;
import ua.nure.bookmeetup.util.PathsUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Log4j2
@Component
public class AdminServiceImpl implements AdminService {

    @Override
    @SneakyThrows
    public void createBackupData() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Path filePath = PathsUtil.getResourcePath("backup/create_backup.bat");
        processBuilder.command(filePath.toString());
        Process process = processBuilder.start();
        logProcess(process);
    }

    @SneakyThrows
    private void logProcess(Process process) {
        if (process.waitFor() == 0) {
            log.info("pg_dump backup is finished");
        } else {
            log.error("pg_dump backup failed");
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            log.error(errorReader.lines().collect(Collectors.joining(" ")).trim());
        }
    }

}
