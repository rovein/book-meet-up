package ua.nure.bookmeetup.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.nure.bookmeetup.dto.EmployeeDto;
import ua.nure.bookmeetup.service.AdminService;
import ua.nure.bookmeetup.service.EmployeeService;
import ua.nure.bookmeetup.util.PathsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@RestController
@CrossOrigin
@RequestMapping(path = "/admin")
@Api(tags = "2. Admin")
@Log4j2
public class AdminController {

    private final EmployeeService employeeService;
    private final AdminService adminService;

    @Autowired
    public AdminController(EmployeeService employeeService, AdminService adminService) {
        this.employeeService = employeeService;
        this.adminService = adminService;
    }

    @PostMapping("/lock-user/{email}")
    @ApiOperation(value = "Locks/unlocks user access to the system", nickname = "lockUser")
    public ResponseEntity<?> lockUser(@PathVariable String email) {
        EmployeeDto employeeDto = employeeService.findByEmail(email);
        boolean reverseLocked = !employeeDto.getLockStatus();
        employeeDto.setLockStatus(reverseLocked);
        return ResponseEntity.ok(employeeService.update(employeeDto));
    }

    @GetMapping("/backup")
    @ApiOperation(value = "Performs data backup and returns .sql dump file", nickname = "getBackupData")
    public ResponseEntity<?> getBackupData() throws IOException {
        adminService.createBackupData();

        Path filePath = PathsUtil.getResourcePath("backup/backup_data.sql");
        File file = new File(filePath.toString());

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=backup_data.sql");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

}
