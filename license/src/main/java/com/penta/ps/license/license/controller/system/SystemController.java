package com.penta.ps.license.license.controller.system;

import com.penta.ps.license.license.dto.common.ServerInfoDto;
import com.penta.ps.license.license.dto.common.UserInfoChangePasswordDto;
import com.penta.ps.license.license.dto.common.UserInfoDto;
import com.penta.ps.license.license.entity.UserInfo;
import com.penta.ps.license.license.exception.common.*;
import com.penta.ps.license.license.repository.UserInfoJpaRepo;
import com.penta.ps.license.license.response.ResponseMessage;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.common.MakeJwt;
import com.penta.ps.license.license.service.common.VerifyJwt;
import com.penta.ps.license.license.session.HttpSessionUtils;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.StatusType;
import com.sun.management.OperatingSystemMXBean;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import static java.lang.Math.round;

@RequiredArgsConstructor
@RestController
public class SystemController {

    private final BuildProperties buildProperties;

    private final UserInfoJpaRepo userInfoJpaRepo;
    private final PasswordEncoder passwordEncoder;

    private final LogModuleService logModuleService;

    private final VerifyJwt verifyJwt;

    @GetMapping(value = "/version")
    public String getVersion() {
        String serverVersion = buildProperties.getVersion();

        StringTokenizer s = new StringTokenizer(serverVersion);
        String verMajor = s.nextToken(".");
        String verMinor = s.nextToken(".");
        String verFeature = s.nextToken(".");
        String verBugFix = s.nextToken(".");

        return "Name: D'Amo v" + verMajor + "." + verMinor + "\n" +
                " |  Products Name: Policy Server v" +  verMajor + "." + verMinor + "\n" +
                " |  Maintenance No: " + verFeature + "." + verBugFix;
    }

    @PostMapping(value = "/token")
    public boolean checkValidToken(@RequestBody Map<String, Object> param) {
        if (param.get("token") == null) {
            return false;
        }

        String token = param.get("token").toString();
        //return verifyJwt.validateToken(token);
        return true;
    }

    // 로그인
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseMessage> login(@RequestBody Map<String, Object> param,
                                                 HttpSession session,
                                                 HttpServletResponse response)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, SSLException {

        int result = 0;

        if (param.get("id") == null || param.get("password") == null) {
            throw new PostToManagerException();
        }

        String id = param.get("id").toString();
        String password = param.get("password").toString();

        Optional<UserInfo> userInfo = Optional.ofNullable(userInfoJpaRepo.findById(id)
                .orElseThrow(AuthenticationEntryPointException::new));

        if (!passwordEncoder.matches(password, userInfo.get().getPassword())) {
            logModuleService.SaveEventLog(LogLevelType.ERROR, "[Login] " + id +
                    " failed to login. Not match password.");
            throw new NotMatchPasswordException();
        }

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, userInfo.get().getId());
        response.setHeader("PENTA-AUTH-TOKEN", MakeJwt.createJwt());

        logModuleService.SaveEventLog(LogLevelType.INFO, "[Login] " + id + " login.");

        if (password.toUpperCase().equals("PENTA")) {
            result = StatusType.FIRST_LOGIN.getCode();
        }

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(result)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @GetMapping("/logouts")
    public ResponseEntity<ResponseMessage> logout(HttpSession session)  {
        if (session.getAttribute("sessionedUser") != null) {
            logModuleService.SaveEventLog(LogLevelType.INFO, "[Logout] " +
                    session.getAttribute("sessionedUser") + " logout.");
        }

        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    // 사용자 생성
    @PostMapping(value = "/signup")
    public ResponseEntity<ResponseMessage> signup(String id,
                                                  String password,
                                                  String rePassword,
                                                  String desc) throws SSLException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {

        if (!password.equals(rePassword)) {
            throw new NotMatchPasswordException();
        }

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .id(id)
                .password(password)
                .build();

        if (userInfoJpaRepo.findById(userInfoDto.getId()).isPresent()) {
            throw new DatabaseViolationException();
        }

        UserInfo userInfo = UserInfo.builder()
                .id(userInfoDto.getId())
                .password(passwordEncoder.encode(userInfoDto.getPassword()))
                .desc(userInfoDto.getDesc())
                .build();

        userInfoJpaRepo.save(userInfo);
        logModuleService.SaveEventLog(LogLevelType.INFO, "[Signup] Create user " + id);

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @GetMapping(value = "/refresh")
    public ResponseEntity<ResponseMessage> refresh(HttpSession session, HttpServletResponse response)
            throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {

        response.setHeader("PENTA-AUTH-TOKEN", MakeJwt.createJwt());
        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();

        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @PostMapping(value = "/password")
    public ResponseEntity<ResponseMessage> changePassword(@RequestBody Map<String, Object> param) throws SSLException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {

        if (param.get("id") == null) {
            throw new PostToManagerException();
        }

        String id = param.get("id").toString();
        String currentPassword = param.get("currentPassword").toString();
        String newPassword = param.get("newPassword").toString();
        String reNewPassword = param.get("reNewPassword").toString();

        UserInfoChangePasswordDto userInfoChangePasswordDto = UserInfoChangePasswordDto.builder()
                .id(id)
                .currentPassword(currentPassword)
                .newPassword(newPassword)
                .build();

        UserInfo userInfo = userInfoJpaRepo.findById(userInfoChangePasswordDto.getId())
                .orElseThrow(AuthenticationEntryPointException::new);
        if (!passwordEncoder.matches(userInfoChangePasswordDto.getCurrentPassword(), userInfo.getPassword())) {
            throw new NotMatchPasswordException();
        }

        if (!newPassword.equals(reNewPassword)) {
            throw new NotMatchNewPasswordException();
        }

        userInfo = UserInfo.builder()
                .id(userInfoChangePasswordDto.getId())
                .password(passwordEncoder.encode(userInfoChangePasswordDto.getNewPassword()))
                .build();
        userInfoJpaRepo.save(userInfo);

        logModuleService.SaveEventLog(LogLevelType.INFO, "[Password] Password changed.");

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();
        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }

    @GetMapping(value = "/systems", produces = "application/json")
    public ResponseEntity<ServerInfoDto> systems() throws IOException {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double usageCpu = round((osBean.getSystemCpuLoad() * 100) * 100 / 100.0);
        double totalMemory = osBean.getTotalPhysicalMemorySize() / 1024 / 1024;
        double freeMemory = osBean.getFreePhysicalMemorySize() / 1024 / 1024;
        double usageMemory = totalMemory - freeMemory;

        File root = new File("/");
        double totalSpace = root.getTotalSpace() / Math.pow(1024, 3);
        double usageSpace = totalSpace - root.getUsableSpace() / Math.pow(1024, 3);

        ServerInfoDto serverInfoDto = ServerInfoDto.builder()
                .usageCpu(usageCpu)
                .totalMemory(totalMemory)
                .usageMemory(usageMemory)
                .freeMemory(freeMemory)
                .totalSpace(totalSpace)
                .usageSpace(usageSpace)
                .build();

        return new ResponseEntity<ServerInfoDto>(serverInfoDto, HttpStatus.OK);
    }

    @PostMapping(value = "/ssl-file")
    public ResponseEntity<ResponseMessage> changeSslFile(@RequestParam("sslFile") MultipartFile sslFile)
            throws IOException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException {

        ApplicationHome home = new ApplicationHome(this.getClass());
        File file = new File(home.getDir() + File.separator + "sslauth");
        file.mkdir();
        sslFile.transferTo(new File(file.getPath() + File.separator + sslFile.getOriginalFilename()));

        logModuleService.SaveEventLog(LogLevelType.INFO, "[Setup] SSL file upload completed.");

        ResponseMessage resMsg = ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build();
        return new ResponseEntity<ResponseMessage>(resMsg, HttpStatus.OK);
    }
}
