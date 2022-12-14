package com.penta.ps.license.license.service.license;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.penta.ps.license.license.dto.contract.ContractExtendedInfoDto;
import com.penta.ps.license.license.dto.contract.ContractInfoJsonDto;
import com.penta.ps.license.license.dto.contract.ContractRegisterGroupDetailInfoDto;
import com.penta.ps.license.license.dto.license.ContactFindAllInfoDto;
import com.penta.ps.license.license.dto.license.LicenseInfoDto;
import com.penta.ps.license.license.dto.license.LicenseRegisterDto;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.exception.common.NotFoundDataException;
import com.penta.ps.license.license.exception.license.ContractInfoMatchFailException;
import com.penta.ps.license.license.exception.license.ResponseBodyNullException;
import com.penta.ps.license.license.exception.license.SearchDataException;
import com.penta.ps.license.license.exception.license.SearchSerialNoException;
import com.penta.ps.license.license.mapper.LicenseMapper;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.contract.ContractInfoJsonService;
import com.penta.ps.license.license.type.ExpireType;
import com.penta.ps.license.license.type.LogLevelType;
import com.penta.ps.license.license.type.TypeDefine;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LicenseService {
    private final ResponseService responseService;
    private final LicenseInfoJpaRepo licenseInfoJpaRepo;
    private final ContractInfoJsonService contractInfoJsonService;
    private final LogModuleService logModuleService;

    public CommonResult putContracts(Integer keyIdx,
                                     String contractFilename,
                                     String beforeLicenseStatus,
                                     String afterLicenseStatus) throws JsonProcessingException {

        ContractInfoJsonDto.ResultDto resultDto = contractInfoJsonService.updateLicenseStatus(
                ContractInfoJsonDto.UpdateLicenseStatusReq
                        .builder()
                        .keyIdx(keyIdx)
                        .contractFileName(contractFilename)
                        .beforeLicenseStatus(beforeLicenseStatus)
                        .afterLicenseStatus(afterLicenseStatus)
                        .build());

        if (resultDto.getResult() != ResponseService.CommonResponse.SUCCESS.getCode())
            throw new ResponseBodyNullException();

        return responseService.getSuccessResult();
    }

    public ContractExtendedInfoDto.ContractExtendedInfo getContractExtendedInfo(String serialNo) throws JsonProcessingException {

        Optional<LicenseInfo> tempLicenseInfo = licenseInfoJpaRepo.findBySerialNo(serialNo);
        if (!tempLicenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        LicenseInfoDto tempLicenseInfoDto = LicenseMapper.INSTANCE.entityToLicenseInfoDto(tempLicenseInfo.get());

        ContractExtendedInfoDto.ContractExtendedInfo contractExtendedInfo
                = contractInfoJsonService.findAllByKeyIdxAndContractFileName(
                tempLicenseInfoDto.getKeyIdx()
                , tempLicenseInfoDto.getContractFileName()
                , tempLicenseInfoDto.getComponentVersion());

        if (contractExtendedInfo.getContractExtendedInfoDto() == null)
            throw new NotFoundDataException();

        // ????????????(??????)
        contractExtendedInfo.getContractExtendedInfoDto().setValidIp(tempLicenseInfoDto.getIpAddress());

        // ????????????(PS)
        contractExtendedInfo.getContractExtendedInfoDto().setSerialNo(serialNo);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        // excluding date on json
        contractExtendedInfo.setStartDate(sdf.format(contractExtendedInfo.getContractExtendedInfoDto().getStartDate()));
        contractExtendedInfo.setEndDate(sdf.format(contractExtendedInfo.getContractExtendedInfoDto().getEndDate()));
        contractExtendedInfo.getContractExtendedInfoDto().setStartDate(null);
        contractExtendedInfo.getContractExtendedInfoDto().setEndDate(null);

        return contractExtendedInfo;

    }

    public int readTimeUnitFromConfFile() throws IOException {
        String line = null;
        String repLine = null;

        ApplicationHome home = new ApplicationHome(this.getClass());
        File file = new File(
                home.getDir() + File.separator + "conf" + File.separator + "license");
        file.mkdir();

        File confFile = new File(
                home.getDir() + File.separator + "conf" + File.separator + "license" + File.separator + "setup.conf");

        if (!confFile.exists()) {
            repLine = "30";
        } else {
            BufferedReader br = new BufferedReader(new FileReader(confFile));

            while ((line = br.readLine()) != null) {
                StringTokenizer s = new StringTokenizer(line);

                if (s.nextToken("=").equals("health-check-interval")) {
                    repLine = s.nextElement().toString();
                    break;
                }
            }
            br.close();

            if (repLine == null)
                repLine = "30";
        }

        return Integer.parseInt(repLine);
    }

    public CommonResult commonLicenseStatusChange()
            throws ParseException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = sdfDate.parse(sdfDate.format(new Date()));
        Date nowDateTime = new Date();

        int intervalMinute = readTimeUnitFromConfFile();
//        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //??? ??????
//        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //??? ??????
//        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //??? ??????
//        long diffDays = diffSec / (24*60*60); //????????? ??????

        // ???????????? ???????????? ????????? ???????????? conf????????? ????????? ??? ????????? ?????? ???????????? ????????????
        List<LicenseInfo> licenseInfos = licenseInfoJpaRepo.findByComponentName(TypeDefine.ProductName.WAPPLES.getOrgName());
        for (LicenseInfo licenseInfo1 : licenseInfos) {
            if (licenseInfo1.getStatus() == TypeDefine.Status.ISSUE_LICENSE ||
                    licenseInfo1.getStatus() == TypeDefine.Status.LICENSE_IN_USE) {
                long diffMin = 0;
                long millisec1 = licenseInfo1.getLastUsageTime().getTime();
                long millisec2 = nowDateTime.getTime();

                if (millisec1 >= millisec2) {
                    // ???????????? ?????? ???????????? ????????? ???????????? ????????? ???????????? ?????? ????????? set???
                    licenseInfo1.setLastUsageTime(new Date());
                    licenseInfoJpaRepo.save(licenseInfo1);
                } else {
                    // ??????????????? ??????????????? ?????? ?????? ???????????? ??????????????? ????????? ?????????
                    diffMin = (millisec2 - millisec1) / 60000;
                    if ((diffMin) > intervalMinute) {
                        licenseInfo1.setStatus(TypeDefine.Status.UNUSED_LICENSE);
                        licenseInfoJpaRepo.save(licenseInfo1);
                        putContracts(licenseInfo1.getKeyIdx(),
                                licenseInfo1.getContractFileName(),
                                "1",
                                "0");
                    }
                }

                // ??????????????? ???????????? ?????????????????? ????????? ??????????????? ???????????????.
                if (licenseInfo1.getLicenseExpiredDate().compareTo(nowDate) < 0) {
                    licenseInfo1.setStatus(TypeDefine.Status.UNUSED_LICENSE);
                    licenseInfoJpaRepo.save(licenseInfo1);
                    putContracts(licenseInfo1.getKeyIdx(),
                            licenseInfo1.getContractFileName(),
                            "1",
                            "0");
                }
            }
        }
        return responseService.getSuccessResult();
    }

    public void setExpireType(List<LicenseInfoDto> licenseDtos) throws ParseException {
        /* Check license valid date */
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String strMonth;
        if (month < 10) {
            strMonth = "0" + month;
        } else {
            strMonth = "" + month;
        }

        String strDay;
        if (day < 10) {
            strDay = "0" + day;
        } else {
            strDay = "" + day;
        }

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = transFormat.parse(year + "" + strMonth + "" + strDay);
        Date now = transFormat.parse(transFormat.format(new Date()));

        for (int i = 0; i < licenseDtos.size(); i++) {
            if (licenseDtos.get(i).getComponentName().contentEquals("WAPPLES")) {
                if (licenseDtos.get(i).getLicenseExpiredDate() != null) {
                    //        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //??? ??????
                    //        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //??? ??????
                    //        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //??? ??????
                    //        long diffDays = diffSec / (24*60*60); //????????? ??????
                    long diffSec = (licenseDtos.get(i).getLicenseExpiredDate().getTime() - now.getTime()) / 1000;
                    long diffDays = diffSec / (24 * 60 * 60); //????????? ??????
                    if (diffDays < 0) {
                        licenseDtos.get(i).setExpireType(ExpireType.EXPIRE);
                    } else {
                        if (diffDays <= 15) {
                            licenseDtos.get(i).setExpireType(ExpireType.TO_EXPIRE);
                        } else {
                            licenseDtos.get(i).setExpireType(ExpireType.VALID);
                        }
                    }
                }
            } else {
                if (licenseDtos.get(i).getLicenseEndDate() != null) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Calendar oneMonthAgoCal = Calendar.getInstance();
                    oneMonthAgoCal.setTime(licenseDtos.get(i).getLicenseEndDate());
                    oneMonthAgoCal.add(Calendar.MONTH, -1);

                    int toExpireYear = oneMonthAgoCal.get(Calendar.YEAR);
                    int toExpireMonth = oneMonthAgoCal.get(Calendar.MONTH) + 1;
                    String strToExpireMonth;
                    if (toExpireMonth < 10) {
                        strToExpireMonth = "0" + toExpireMonth;
                    } else {
                        strToExpireMonth = "" + toExpireMonth;
                    }

                    int toExpireDate = oneMonthAgoCal.get(Calendar.DAY_OF_MONTH);
                    String strToExpireDate;
                    if (toExpireDate < 10) {
                        strToExpireDate = "0" + toExpireDate;
                    } else {
                        strToExpireDate = "" + toExpireDate;
                    }

                    Date toExpireDay = transFormat.parse(toExpireYear + "" + strToExpireMonth + "" + strToExpireDate);

                    if (today.compareTo(toExpireDay) > 0 && today.compareTo(licenseDtos.get(i).getLicenseEndDate()) <= 0) {
                        licenseDtos.get(i).setExpireType(ExpireType.TO_EXPIRE);
                    } else if (today.compareTo(licenseDtos.get(i).getLicenseEndDate()) > 0) {
                        licenseDtos.get(i).setExpireType(ExpireType.EXPIRE);
                    } else {
                        licenseDtos.get(i).setExpireType(ExpireType.VALID);
                    }
                }
            }
        }
    }

    public void updateLicense(List<LicenseRegisterDto> licenseRegisterDtos) throws JsonProcessingException, ParseException {

        int matchCount = 0;

        DateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        for (LicenseRegisterDto licenseRegisterDto : licenseRegisterDtos) {
            // 1. license_info ???????????? ?????? serialno??? ?????? ??????????????? ??????
            Optional<LicenseInfo> licenseInfo1 = licenseInfoJpaRepo.findBySerialNo(licenseRegisterDto.getSerialNo());

            // license_info ???????????? ?????? ????????? ???????????????.
            if (!licenseInfo1.isPresent()) {
                throw new SearchSerialNoException();
            }

            // ?????? ???????????? ??????????????? ?????? ????????????????????? ????????? ??????????????? ???????????? ??????(contractFileName, KeyIdx ??????)
            // 2. contract module??? ?????? ???????????? ??????????????? ??????(????????? ???????????????)
            ContractInfoJsonDto.FindAllContractInfoDtoResp findAllContractInfoDtoResp =
                    contractInfoJsonService.findContractRegisterGroupDetailInfo(
                            ContractRegisterGroupDetailInfoDto
                                    .builder()
                                    .custNm(licenseRegisterDto.getCustNm())
                                    .endUserId(licenseRegisterDto.getEndUserId())
                                    .estiType(licenseRegisterDto.getEstiType())
                                    .orderNum(licenseRegisterDto.getOrderNum())
                                    .startDate(licenseRegisterDto.getStartDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                    .endDate(licenseRegisterDto.getEndDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                    .lisProNm(licenseRegisterDto.getLisProNm())
                                    .kmsId(licenseRegisterDto.getKmsId())
                                    .hwSn(licenseRegisterDto.getHwSn())
                                    .cpuCount(licenseRegisterDto.getCpuCount())
                                    .lisVerUp(licenseRegisterDto.getLisVerUp())
                                    .lisTypeOcUp(licenseRegisterDto.getLisTypeOcUp())
                                    .cloudSp(licenseRegisterDto.getCloudSp())
                                    .cloudCsi(licenseRegisterDto.getCloudCsi())
                                    .cloudSgn(licenseRegisterDto.getCloudSgn())
                                    .instanceNm(licenseRegisterDto.getInstanceNm())
                                    .symApiYn(licenseRegisterDto.getSymApiYn())
                                    .pubApiYn(licenseRegisterDto.getPubApiYn())
                                    .kewinApiYn(licenseRegisterDto.getKewinApiYn())
                                    .apiYn(licenseRegisterDto.getApiYn())
                                    .kmsYn(licenseRegisterDto.getKmsYn())
                                    .osYn(licenseRegisterDto.getOsYn())
                                    .lisOsType(licenseRegisterDto.getLisOsType())
                                    .kmYn(licenseRegisterDto.getKmYn())
                                    .fileYn(licenseRegisterDto.getFileYn())
                                    .hashYn(licenseRegisterDto.getHashYn())
                                    .kmsEnc(licenseRegisterDto.getKmsEnc())
                                    .kmsPubKey(licenseRegisterDto.getKmsPubKey())
                                    .kmsPriKey(licenseRegisterDto.getKmsPriKey())
                                    .build());

            int matchYn = 0;
            LicenseInfo licenseInfo = null;
            Calendar cal = Calendar.getInstance();
            Date now = sdf.parse(sdf.format(new Date()));

            if (findAllContractInfoDtoResp.getResultDto().getResult() != 0
                    || findAllContractInfoDtoResp.getFindAllContractInfoDtos() == null) {
                throw new SearchDataException();
            }

            for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : findAllContractInfoDtoResp.getFindAllContractInfoDtos()) {
                // mertering ?????? ???????????? ?????? ??????(???????????? ??????)
                if (!contractInfoJsonDataDto.getLisTypeOcUp().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.name())) {
                    // 3. ?????? ??????????????? ???????????? ???????????? ????????? ??????????????? ?????? ??????????????? ?????????.
                    if (contractInfoJsonDataDto.getLicenseStatus().contentEquals("1"))
                        continue;
                }

                // metering??? ?????? ?????? ???????????? ????????? ????????? ?????? ??????????????? ??????????????? ????????????.
                if (licenseInfo1.get().getKeyIdx() != null
                        && !licenseInfo1.get().getContractFileName().isEmpty()
                        && !licenseInfo1.get().getContractFileName().equals(null)
                        && !licenseInfo1.get().getLicenseType().getName().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.getName())) {
                    // ????????? ?????????????????? ??????????????? ???????????? ???????????? ?????? ??????????????? ??????????????? ????????????.
                    putContracts(licenseInfo1.get().getKeyIdx(),
                            licenseInfo1.get().getContractFileName(),
                            "1",
                            "0");
                }

                if (contractInfoJsonDataDto.getLisProNm().contentEquals(TypeDefine.ProductName.WAPPLES.getOrgName())) {
                    if (((sdf.parse(contractInfoJsonDataDto.getLisEDate()).getTime() - now.getTime()) / (1000 * 24 * 60 * 60)) <= 30) {
                        cal.setTime(sdf.parse(contractInfoJsonDataDto.getLisEDate()));
                    } else {
                        cal.setTime(new Date());
                        cal.add(Calendar.DATE, 30);
                    }
                } else {
                    cal.setTime(sdf.parse(contractInfoJsonDataDto.getLisEDate()));
                }

                LicenseInfo licenseInfo2 = licenseInfo1.get();
                licenseInfo2.setFirstUsageTime(licenseRegisterDto.getFirstUsageTime());
                licenseInfo2.setLastUsageTime(licenseRegisterDto.getFirstUsageTime());
                licenseInfo2.setLicenseIssueDate(licenseRegisterDto.getLicenseIssueDate());
                licenseInfo2.setLicenseExpiredDate(cal.getTime());
                licenseInfo2.setLicenseStartDate(licenseRegisterDto.getLicenseStartDate());
                licenseInfo2.setLicenseEndDate(licenseRegisterDto.getLicenseEndDate());
                licenseInfo2.setStatus(licenseRegisterDto.getStatus());
                licenseInfo2.setContractFileName(contractInfoJsonDataDto.getContractFileName());
                licenseInfo2.setKeyIdx(contractInfoJsonDataDto.getKeyIdx());
                licenseInfo2.setCustNm(licenseRegisterDto.getCustNm());
                licenseInfo2.setOrderNum(licenseRegisterDto.getOrderNum());
                licenseInfo2.setLicenseType(TypeDefine.LicenseType.getLicenseType(licenseRegisterDto.getLisTypeOcUp()));
                licenseInfo2.setInstanceNm(licenseRegisterDto.getInstanceNm());
                licenseInfo2.setSymApiYn(licenseRegisterDto.getSymApiYn());
                licenseInfo2.setPubApiYn(licenseRegisterDto.getPubApiYn());
                licenseInfo2.setKewinApiYn(licenseRegisterDto.getKewinApiYn());
                licenseInfo2.setApiYn(licenseRegisterDto.getApiYn());
                licenseInfo2.setKmsYn(licenseRegisterDto.getKmsYn());
                licenseInfo2.setOsYn(licenseRegisterDto.getOsYn());
                licenseInfo2.setLisOsType(licenseRegisterDto.getLisOsType());
                licenseInfo2.setKmYn(licenseRegisterDto.getKmYn());
                licenseInfo2.setFileYn(licenseRegisterDto.getFileYn());
                licenseInfo2.setHashYn(licenseRegisterDto.getHashYn());
                licenseInfo2.setKmsEnc(licenseRegisterDto.getKmsEnc());
                licenseInfo2.setKmsPubKey(licenseRegisterDto.getKmsPubKey());
                licenseInfo2.setKmsPriKey(licenseRegisterDto.getKmsPriKey());

                if (licenseRegisterDto.getLisTypeOcUp().contentEquals(TypeDefine.LicenseType.COMMON_COMMERCIAL.name())) {
                    licenseInfo2.setIpAddress(contractInfoJsonDataDto.getCustIp());
                }
                licenseInfo2.setWebCnt(contractInfoJsonDataDto.getWebCnt());
                licenseInfo2.setLisExpAct(contractInfoJsonDataDto.getLisExpAct());

                if (contractInfoJsonDataDto.getLisProNm().contentEquals(TypeDefine.ProductName.WAPPLES.getOrgName())) {
                    licenseInfo2.setCore(licenseRegisterDto.getCpuCount());
                }

                licenseInfoJpaRepo.save(licenseInfo2);

                if (!licenseInfo2.getLicenseType().getName().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.getName())) {
                    // ????????? ??????????????? ??????????????? ????????????.
                    putContracts(licenseInfo2.getKeyIdx(),
                            licenseInfo2.getContractFileName(),
                            "0",
                            "1");
                }

                matchYn = 1;
                matchCount++;
                break;
            }
            // 5. license_info ????????? contract_info ????????? ???????????? ????????? ????????????
            if (matchYn == 0)
                throw new ContractInfoMatchFailException();
        }

        // 6. ???????????? ????????? ??????????????? ????????? ????????? ????????????
        if (licenseRegisterDtos.size() != matchCount)
            throw new ContractInfoMatchFailException();

        for (LicenseRegisterDto licenseRegisterDto : licenseRegisterDtos) {
            logModuleService.SavePolicyLog(LogLevelType.INFO,
                    "[Licenses] " + licenseRegisterDto.getSerialNo() + " status changed to " +
                            licenseRegisterDto.getStatus());
        }
    }

    public List<LicenseInfoDto> searchLicense(int currentPageNo,
                                              int recordsPerPage,
                                              String serialNo,
                                              TypeDefine.LicenseType licenseType,
                                              String componentName,
                                              String componentVersion,
                                              String ipAddress) {
        Page<LicenseInfo> pageResult = null;
        int paramCombination = 0;

        if (serialNo != null) paramCombination += 1;
        if (licenseType != TypeDefine.LicenseType.NOT_FOUND) paramCombination += 2;
        if (componentName != null) paramCombination += 4;
        if (componentVersion != null) paramCombination += 8;
        if (ipAddress != null) paramCombination += 16;

        switch (paramCombination) {
            case 1:
                pageResult = licenseInfoJpaRepo.findBySerialNoContainingIgnoreCase(serialNo, PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;

            case 2:
                pageResult = licenseInfoJpaRepo.findByLicenseType(licenseType, PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;

            case 4:
                pageResult = licenseInfoJpaRepo.findByComponentNameContainingIgnoreCase(componentName, PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;

            case 8:
                pageResult = licenseInfoJpaRepo.findByComponentVersionContainingIgnoreCase(componentVersion, PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;

            case 16:
                pageResult = licenseInfoJpaRepo.findByIpAddressContainingIgnoreCase(ipAddress, PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;

            default:
                pageResult = licenseInfoJpaRepo.findByLicenseTypeOrSerialNoContainingOrComponentNameContainingOrComponentVersionContainingOrIpAddressContaining(
                        licenseType,
                        serialNo,
                        componentName,
                        componentVersion,
                        ipAddress,
                        PageRequest.of(currentPageNo - 1, recordsPerPage));
                break;
        }

        return LicenseMapper.INSTANCE.entitiesToLicenseInfoDtos(pageResult.toList());
    }
}
