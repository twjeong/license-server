package com.penta.ps.license.license.controller.license;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.penta.ps.license.license.dto.contract.ContractInfoJsonDto;
import com.penta.ps.license.license.dto.contract.ContractRegisterGroupDetailInfoDto;
import com.penta.ps.license.license.dto.license.*;
import com.penta.ps.license.license.entity.LicenseInfo;
import com.penta.ps.license.license.exception.license.ContractInfoMatchFailException;
import com.penta.ps.license.license.exception.license.SearchDataException;
import com.penta.ps.license.license.exception.license.SearchSerialNoException;
import com.penta.ps.license.license.mapper.LicenseMapper;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.response.ListResultWithPageTotalSize;
import com.penta.ps.license.license.response.ResponseLicense;
import com.penta.ps.license.license.response.ResponseMessage;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.contract.ContractInfoJsonService;
import com.penta.ps.license.license.service.license.LicenseService;
import com.penta.ps.license.license.type.ExpireType;
import com.penta.ps.license.license.type.TypeDefine;
import com.penta.ps.license.license.type.LogLevelType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class LicenseController {

    private final ResponseService responseService;
    private final LicenseInfoJpaRepo licenseInfoJpaRepo;
    private final LogModuleService logModuleService;
    private final LicenseService licenseService;
    private final ContractInfoJsonService contractInfoJsonService;

    @Transactional(readOnly = true)
    @GetMapping(value = "/licenses", produces = "application/json")
    public ResponseEntity<ResponseLicense> getAllLicense(
            @RequestParam(value = "current-page-no") int currentPageNo,
            @RequestParam(value = "records-per-page") int recordsPerPage) throws InvalidAlgorithmParameterException, InvalidKeySpecException, NoSuchAlgorithmException, ParseException, IOException {

        Page<LicenseInfo> pageResult = null;

        // ???????????? ????????? ????????????
        getCommonLicenseExpiredDataSearch();

        pageResult = licenseInfoJpaRepo.findAll(PageRequest.of(currentPageNo - 1, recordsPerPage));

        List<LicenseInfoDto> licenseInfoList = LicenseMapper.INSTANCE.entitiesToLicenseInfoDtos(pageResult.toList());

        // set expire type
        licenseService.setExpireType(licenseInfoList);

        ResponseLicense responseLicense = ResponseLicense.builder()
                .licenseList(licenseInfoList.stream().toArray(LicenseInfoDto[]::new))
                .build();

        return new ResponseEntity<>(responseLicense, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/specific-licenses")
    public ResponseEntity<ResponseLicense> searchLicenseWithCondition(
            @RequestParam(value = "current-page-no", defaultValue = "1") int currentPageNo,
            @RequestParam(value = "records-per-page", defaultValue = "65535") int recordsPerPage,
            @RequestParam(value = "serial-no", required = false) String serialNo,
            @RequestParam(value = "license-type", defaultValue = "NOT_FOUND") TypeDefine.LicenseType licenseType,
            @RequestParam(value = "component-name", required = false) String componentName,
            @RequestParam(value = "component-version", required = false) String componentVersion,
            @RequestParam(value = "ip-address", required = false) String ipAddress
    ) {

        List<LicenseInfoDto> licenseInfoList = licenseService.searchLicense(currentPageNo,
                recordsPerPage,
                serialNo,
                licenseType,
                componentName,
                componentVersion,
                ipAddress);

        ResponseLicense responseLicense = ResponseLicense.builder()
                .licenseList(licenseInfoList.stream().toArray(LicenseInfoDto[]::new))
                .build();

        return new ResponseEntity<>(responseLicense, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/licenses")
    public CommonResult addLicense(@RequestBody LicenseInfoDto licenseInfoDto) {
        // enum ????????? ?????? ?????? ?????? ??? json parse ???????????? ????????? ?????????
        String serialNo = UUID.randomUUID().toString();

        licenseInfoDto.setSerialNo(serialNo);

        LicenseInfo licenseInfo = LicenseInfo.builder()
                .serialNo(licenseInfoDto.getSerialNo())
                .componentName(TypeDefine.ProductName.getFromNewProductNameToOrgProductName(licenseInfoDto.getComponentName()))
                .componentVersion(licenseInfoDto.getComponentVersion())
                .ipAddress(licenseInfoDto.getIpAddress())
                .core(licenseInfoDto.getCore())
                .licenseFile(licenseInfoDto.getLicenseFile())
                .status(TypeDefine.Status.getStatus(licenseInfoDto.getStatus()))
                .build();
        licenseInfoJpaRepo.save(licenseInfo);

        logModuleService.SavePolicyLog(LogLevelType.INFO,
                "[Licenses] " + licenseInfoDto.getSerialNo() + " status changed to " +
                        licenseInfoDto.getStatus());

        return responseService.getSingleResultWithSerialNo(serialNo);
    }

    @Transactional
    @PostMapping("/common-agent/common-licenses")
    public CommonResult addCommonLicense(@RequestBody LicenseInfoDto licenseInfoDto) throws ParseException,
            JsonProcessingException {
        String serialNo = UUID.randomUUID().toString();

        LicenseInfo licenseInfo = null;

        // contractNumber(orderNum)??? ?????? ??? ?????? ????????????, ???????????? ?????????????????? Web UI??? ?????? ??????????????? ????????????.
        if (licenseInfoDto.getOrderNum() != null &&
                !licenseInfoDto.getOrderNum().isEmpty() &&
                licenseInfoDto.getOrderNum().length() != 0) {
            // 1. license_info ???????????? ?????? serialno??? ?????? ??????????????? ??????
            List<LicenseInfo> licenseInfos = licenseInfoJpaRepo.findByNodeId(licenseInfoDto.getNodeId());

            // license_info ???????????? ?????? ????????? ?????? ?????? ?????? ???????????? ??????, ??????????????? ?????????
            for (LicenseInfo licenseInfo1 : licenseInfos) {
                if ((licenseInfo1.getStatus().getName().contentEquals(TypeDefine.Status.ISSUE_LICENSE.getName()) ||
                        licenseInfo1.getStatus().getName().contentEquals(TypeDefine.Status.LICENSE_IN_USE.getName())) &&
                        licenseInfo1.getOrderNum().contentEquals(licenseInfoDto.getOrderNum()) &&
                        licenseInfo1.getComponentName().contentEquals(licenseInfoDto.getComponentName()) &&
                        licenseInfo1.getComponentVersion().contentEquals(licenseInfoDto.getComponentVersion()) &&
                        licenseInfo1.getCore() == licenseInfoDto.getCore()) {
                    logModuleService.SavePolicyLog(LogLevelType.INFO,
                            "[Licenses] " + licenseInfo1.getSerialNo() + " Already Exist license " +
                                    licenseInfo1.getStatus());
                    return responseService.getSingleResultWithLicenseId(licenseInfo1.getSerialNo());
                }
            }

            // ????????????
            // 2. contract ??? ?????? ???????????? ??????????????? ??????(????????? ???????????????)
            ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllContractInfoDtoResp =
                    contractInfoJsonService.findAllPageByOrderNumAndLisProNm(
                            1,
                            65535,
                            licenseInfoDto.getOrderNum(),
                            TypeDefine.ProductName.getFromNewProductNameToOrgProductName(licenseInfoDto.getComponentName()));

            if (findAllContractInfoDtoResp.getResultDto().getResult() != 0 ||
                    findAllContractInfoDtoResp.getFindAllContractInfoDtos() == null) {
                throw new SearchDataException();
            }

            int matchYn = 0;

            Calendar cal = Calendar.getInstance();
            DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date now = sdf.parse(sdf.format(new Date()));

            for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : findAllContractInfoDtoResp.getFindAllContractInfoDtos()) {
                // mertering ?????? ???????????? ?????? ??????(???????????? ??????)
                if (!contractInfoJsonDataDto.getLisTypeOcUp().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.name())) {
                    // 3. ?????? ??????????????? ???????????? ???????????? ????????? ??????????????? ?????? ??????????????? ?????????.
                    // 0:???????????? ????????? , 1:???????????? ?????????
                    if (contractInfoJsonDataDto.getLicenseStatus().contentEquals("1"))
                        continue;
                }

                //        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //??? ??????
                //        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //??? ??????
                //        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //??? ??????
                //        long diffDays = diffSec / (24*60*60); //????????? ??????
                if (licenseInfoDto.getComponentName().contentEquals(TypeDefine.ProductName.WAPPLES.getNewName())) {
                    if (((sdf.parse(contractInfoJsonDataDto.getLisEDate()).getTime() - now.getTime()) / (1000 * 24 * 60 * 60)) <= 30) {
                        cal.setTime(sdf.parse(contractInfoJsonDataDto.getLisEDate()));
                        cal.add(Calendar.HOUR, 23);
                        cal.add(Calendar.MINUTE, 59);
                        cal.add(Calendar.SECOND, 59);
                    } else {
                        cal.setTime(now);
                        cal.add(Calendar.DATE, 30);
                        cal.add(Calendar.HOUR, 23);
                        cal.add(Calendar.MINUTE, 59);
                        cal.add(Calendar.SECOND, 59);
                    }
                } else {
                    cal.setTime(sdf.parse(contractInfoJsonDataDto.getLisEDate()));
                    cal.add(Calendar.HOUR, 23);
                    cal.add(Calendar.MINUTE, 59);
                    cal.add(Calendar.SECOND, 59);
                }

                licenseInfo =
                        LicenseInfo.builder()
                                .serialNo(serialNo)
                                .nodeId(licenseInfoDto.getNodeId())
                                .keyIdx(contractInfoJsonDataDto.getKeyIdx())
                                .contractFileName(contractInfoJsonDataDto.getContractFileName())
                                .custNm(contractInfoJsonDataDto.getCustNm())
                                .orderNum(contractInfoJsonDataDto.getOrderNum())
                                .licenseType(TypeDefine.LicenseType.getLicenseType(contractInfoJsonDataDto.getLisTypeOcUp()))
                                .componentName(licenseInfoDto.getComponentName())
                                .componentVersion(licenseInfoDto.getComponentVersion())
                                .ipAddress(licenseInfoDto.getIpAddress())
                                .core(contractInfoJsonDataDto.getLoCore())
                                .instanceNm(contractInfoJsonDataDto.getHwSn())
                                .firstUsageTime(new Date())
                                .lastUsageTime(new Date())
                                .hoursOfUsageTime(0)
                                .licenseIssueDate(now)
                                .licenseExpiredDate(cal.getTime())
                                .status(TypeDefine.Status.ISSUE_LICENSE)
                                .symApiYn(contractInfoJsonDataDto.getSymApiYn())
                                .pubApiYn(contractInfoJsonDataDto.getPubApiYn())
                                .kewinApiYn(contractInfoJsonDataDto.getKewinApiYn())
                                .apiYn(contractInfoJsonDataDto.getApiYn())
                                .kmsYn(contractInfoJsonDataDto.getKmsYn())
                                .osYn(contractInfoJsonDataDto.getOsYn())
                                .lisOsType(contractInfoJsonDataDto.getLisOsType())
                                .kmYn(contractInfoJsonDataDto.getKmYn())
                                .fileYn(contractInfoJsonDataDto.getFileYn())
                                .hashYn(contractInfoJsonDataDto.getHashYn())
                                .kmsEnc(contractInfoJsonDataDto.getKmsEnc())
                                .kmsPubKey(contractInfoJsonDataDto.getKmsPubKey())
                                .kmsPriKey(contractInfoJsonDataDto.getKmsPriKey())
                                .webCnt(contractInfoJsonDataDto.getWebCnt())
                                .lisExpAct(contractInfoJsonDataDto.getLisExpAct())
                                .build();

                if (contractInfoJsonDataDto.getEstiType().contentEquals("10")) {
                    licenseInfo.setLicenseStartDate(sdf.parse(contractInfoJsonDataDto.getLisSDate()));
                    licenseInfo.setLicenseEndDate(sdf.parse(contractInfoJsonDataDto.getLisEDate()));
                } else {
                    licenseInfo.setLicenseStartDate(sdf.parse(contractInfoJsonDataDto.getInsSDt()));
                    licenseInfo.setLicenseEndDate(sdf.parse(contractInfoJsonDataDto.getInsEDt()));
                }

                licenseInfoJpaRepo.save(licenseInfo);

                if (!licenseInfo.getLicenseType().getName().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.getName())) {
                    // ????????? ??????????????? ??????????????? ????????????.
                    licenseService.putContracts(licenseInfo.getKeyIdx(),
                            licenseInfo.getContractFileName(),
                            "0",
                            "1");
                }

                matchYn = 1;
                break;
            }
            // 5. license_info ????????? contract_info ????????? ???????????? ????????? ????????????
            if (matchYn == 0)
                throw new ContractInfoMatchFailException();
        } else {
            licenseInfo =
                    LicenseInfo.builder()
                            .serialNo(serialNo)
                            .nodeId(licenseInfoDto.getNodeId())
                            .orderNum(licenseInfoDto.getOrderNum())
                            .componentName(TypeDefine.ProductName.getFromNewProductNameToOrgProductName(licenseInfoDto.getComponentName()))
                            .componentVersion(licenseInfoDto.getComponentVersion())
                            .core(licenseInfoDto.getCore())
                            .ipAddress(licenseInfoDto.getIpAddress())
                            .status(TypeDefine.Status.REGISTER_COMPONENT)
                            .build();

            licenseInfoJpaRepo.save(licenseInfo);
        }


        logModuleService.SavePolicyLog(LogLevelType.INFO,
                "[Licenses] " + licenseInfo.getSerialNo() + " status changed to " +
                        licenseInfo.getStatus());

        return responseService.getSingleResultWithLicenseId(licenseInfo.getSerialNo());
    }

    @PutMapping("/licenses")
    public ResponseEntity<ResponseMessage> updateLicense(@RequestBody List<LicenseRegisterDto> licenseDtos)
            throws ParseException, JsonProcessingException {
        boolean alreadyIssueLicense = false;

        List<LicenseInfoDto> alreadyLicenseDtos = licenseService.searchLicense(1,
                65535,
                licenseDtos.get(0).getSerialNo(),
                TypeDefine.LicenseType.NOT_FOUND,
                null,
                null,
                null);

        if (alreadyLicenseDtos.size() > 0) {
            if (alreadyLicenseDtos.get(0).getLicenseType() != null) {
                alreadyIssueLicense = true;
            }
        }

        licenseService.updateLicense(licenseDtos);

        for (LicenseRegisterDto licenseRegisterDto : licenseDtos) {
            if (alreadyIssueLicense) {
                logModuleService.SavePolicyLog(LogLevelType.INFO,
                        "[Licenses] Issued a license to " + licenseRegisterDto.getSerialNo() + "(renewal).");
            } else {
                logModuleService.SavePolicyLog(LogLevelType.INFO,
                        "[Licenses] Issued a license to " + licenseRegisterDto.getSerialNo() + ".");
            }
        }

        return new ResponseEntity<>(ResponseMessage.builder()
                .result(0)
                .msg("Success")
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/licenses")
    public CommonResult deleteLicense(HttpServletRequest req,
                                      @RequestBody List<LicenseInfoDto> licenseInfoDtos) throws JsonProcessingException {
        // ?????? ?????? ????????? param ?????? ??? ?????? ?????????

        for (LicenseInfoDto licenseInfoDto : licenseInfoDtos) {
            Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(licenseInfoDto.getSerialNo());

            if (!licenseInfo.isPresent()) {
                throw new SearchSerialNoException();
            }

            LicenseInfo licenseInfo1 = licenseInfo.get();

            // ???????????? ????????? ????????? ?????? ??????????????? ??????????????? ????????????.
            if (licenseInfo.get().getKeyIdx() != null
                    && !licenseInfo.get().getContractFileName().isEmpty()
                    && !licenseInfo.get().getContractFileName().equals(null)
                    && !licenseInfo1.getLicenseType().getName().contentEquals(TypeDefine.LicenseType.CLOUD_METERING.getName())) {
                // ????????? ?????????????????? ??????????????? ???????????? ???????????? ?????? ??????????????? ??????????????? ????????????.
                licenseService.putContracts(licenseInfo.get().getKeyIdx(),
                        licenseInfo.get().getContractFileName(),
                        "1",
                        "0");
            }

            licenseInfo1.setKeyIdx(null);
            licenseInfo1.setContractFileName(null);
            licenseInfo1.setStatus(TypeDefine.Status.UNUSED_LICENSE);

            licenseInfoJpaRepo.save(licenseInfo1);


            logModuleService.SavePolicyLog(LogLevelType.INFO,
                    "[Licenses] " + licenseInfo1.getSerialNo() + " status changed to " +
                            licenseInfo1.getStatus());

            logModuleService.SavePolicyLog(LogLevelType.INFO,
                    "[Licenses] " + licenseInfo1.getSerialNo() + " deleted.");
        }

        return responseService.getSuccessResult();
    }


    // ???????????? ????????? ?????? ????????????, cloud ???????????? ??????
    @Transactional(readOnly = true)
    @GetMapping("/licenses/license/service-count-in-use")
    public CommonResult getServiceCountInUse(@RequestParam(value = "contract-file-name", required = true) List<String> fileNameList) {

        HashMap<String, Object> hashMapResponse = new HashMap<>();
        List<ServiceCountDto> serviceCountDtos = new ArrayList<>();

        for (String contractFileName : fileNameList) {
            serviceCountDtos = licenseInfoJpaRepo.getServiceCount(TypeDefine.Status.LICENSE_IN_USE, contractFileName, TypeDefine.LicenseType.CLOUD_METERING);
            hashMapResponse.put(contractFileName, serviceCountDtos);
        }

        return responseService.getMultipleResultWithServiceCountInUse(hashMapResponse);
    }

    // ???????????? ?????? ?????? ????????????, ????????? ??????
    @Transactional(readOnly = true)
    @GetMapping("/licenses/contract/ticket-and-cloud-info")
    public CommonResult getContractServiceInfo(@RequestParam(value = "cust_nm", required = true) String customer) {

        HashMap<String, Object> hashMapResponse = new HashMap<>();

        List<ContractServiceInfoDto> contractServiceInfoDtos = new ArrayList<>();
        contractServiceInfoDtos = licenseInfoJpaRepo.getContractServiceInfo(TypeDefine.Status.LICENSE_IN_USE, customer);

        List<ContractTicketServiceDto> contractTicketServiceDtos = new ArrayList<>();
        List<ContractCloudServiceDto> contractCloudServiceDtos = new ArrayList<>();

        for (ContractServiceInfoDto contractServiceInfoDto : contractServiceInfoDtos) {
            if (contractServiceInfoDto.getLicenseType() != TypeDefine.LicenseType.CLOUD_METERING) {
                // ??????
                ContractTicketServiceDto tempTicketServiceDto = contractTicketServiceDtos
                        .stream()
                        .filter(list -> list.getComponentName().equals(contractServiceInfoDto.getComponentName()))
                        .findAny()
                        .orElse(null);

                if (tempTicketServiceDto == null) {
                    contractTicketServiceDtos.add(new ContractTicketServiceDto(contractServiceInfoDto.getComponentName()
                            , contractServiceInfoDto.getInUseCount(), contractServiceInfoDto.getIssuedCount()));
                } else {
                    for (ContractTicketServiceDto contractTicketServiceDto : contractTicketServiceDtos) {
                        if (contractTicketServiceDto.getComponentName().equals(contractServiceInfoDto.getComponentName())) {
                            contractTicketServiceDto.setTicketInUse(contractTicketServiceDto.getTicketInUse() + contractServiceInfoDto.getInUseCount());
                            contractTicketServiceDto.setAllTickets(contractTicketServiceDto.getAllTickets() + contractServiceInfoDto.getIssuedCount());
                            break;
                        }
                    }
                }
            } // end of if (ticket?)
            else {
                // ????????????
                ContractCloudServiceDto tempColudServiceDto = contractCloudServiceDtos
                        .stream()
                        .filter(list -> list.getComponentName().equals(contractServiceInfoDto.getComponentName()))
                        .findAny()
                        .orElse(null);

                if (tempColudServiceDto == null) {
                    contractCloudServiceDtos.add(new ContractCloudServiceDto(contractServiceInfoDto.getComponentName()
                            , contractServiceInfoDto.getSumOfUsageTime()));
                } else {
                    for (ContractCloudServiceDto contractCloudServiceDto : contractCloudServiceDtos) {
                        if (contractCloudServiceDto.getComponentName().equals(contractServiceInfoDto.getComponentName())) {
                            contractCloudServiceDto.setSumOfUsageTime(contractCloudServiceDto.getSumOfUsageTime() + contractServiceInfoDto.getSumOfUsageTime());
                            break;
                        }
                    }
                }
            } // end of if (cloud?)
        } // end of for

        hashMapResponse.put("ticket", contractTicketServiceDtos);
        hashMapResponse.put("cloud", contractCloudServiceDtos);

        return responseService.getMultipleResultWithServiceInfoInUse(hashMapResponse);
    }

    // CM?????? status ?????? update ?????? ??? ????????? ?????? ??????
    @Transactional
    @PutMapping("/licenses/license/status")
    public CommonResult updateStatusOfLicenseInfo(@RequestBody LicenseInfoDto licenseInfoDto) {
        Optional<LicenseInfo> tempLicenseInfo = licenseInfoJpaRepo.findBySerialNo(licenseInfoDto.getSerialNo());

        if (!tempLicenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        LicenseInfoDto tempLicenseInfoDto = LicenseMapper.INSTANCE.entityToLicenseInfoDto(tempLicenseInfo.get());
        tempLicenseInfoDto.setStatus(licenseInfoDto.getStatus());

        licenseInfoJpaRepo.save(LicenseInfo.builder()
                .serialNo(tempLicenseInfoDto.getSerialNo())
                .keyIdx(tempLicenseInfoDto.getKeyIdx())
                .contractFileName(tempLicenseInfoDto.getContractFileName())
                .custNm(tempLicenseInfoDto.getCustNm())
                .orderNum(tempLicenseInfoDto.getOrderNum())
                .licenseType(tempLicenseInfoDto.getLicenseType())
                .componentName(tempLicenseInfoDto.getComponentName())
                .componentVersion(tempLicenseInfoDto.getComponentVersion())
                .ipAddress(tempLicenseInfoDto.getIpAddress())
                .core(tempLicenseInfoDto.getCore())
                .instanceNm(tempLicenseInfo.get().getInstanceNm())
                .licenseFile(tempLicenseInfoDto.getLicenseFile())
                .firstUsageTime(tempLicenseInfoDto.getFirstUsageTime())
                .hoursOfUsageTime(tempLicenseInfoDto.getHoursOfUsageTime())
                .licenseIssueDate(tempLicenseInfoDto.getLicenseIssueDate())
                .licenseExpiredDate(tempLicenseInfoDto.getLicenseExpiredDate())
                .licenseStartDate(tempLicenseInfoDto.getLicenseStartDate())
                .licenseEndDate(tempLicenseInfoDto.getLicenseEndDate())
                .status(TypeDefine.Status.getStatus(tempLicenseInfoDto.getStatus()))
                .symApiYn(tempLicenseInfoDto.getSymApiYn())
                .pubApiYn(tempLicenseInfoDto.getPubApiYn())
                .kewinApiYn(tempLicenseInfoDto.getKewinApiYn())
                .apiYn(tempLicenseInfoDto.getApiYn())
                .kmsYn(tempLicenseInfoDto.getKmsYn())
                .osYn(tempLicenseInfoDto.getOsYn())
                .lisOsType(tempLicenseInfoDto.getLisOsType())
                .kmYn(tempLicenseInfoDto.getKmYn())
                .fileYn(tempLicenseInfoDto.getFileYn())
                .hashYn(tempLicenseInfoDto.getHashYn())
                .kmsEnc(tempLicenseInfoDto.getKmsEnc())
                .kmsPubKey(tempLicenseInfoDto.getKmsPubKey())
                .kmsPriKey(tempLicenseInfoDto.getKmsPriKey())
                .build());

        logModuleService.SavePolicyLog(LogLevelType.INFO,
                "[Licenses] " + tempLicenseInfoDto.getSerialNo() + " status changed to " +
                        tempLicenseInfoDto.getStatus());

        return responseService.getSuccessResult();
    }

    @Transactional(readOnly = true)
    @GetMapping("/licenses/license/status")
    public CommonResult getStatusOfLicenseInfo(@RequestParam(value = "serial-no", defaultValue = "0") String serialNo) {

        Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(serialNo);

        if (!licenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        return responseService.getMultipleResultWithStatusNLicenseType(
                TypeDefine.Status.getname(licenseInfo.get().getStatus()),
                TypeDefine.LicenseType.getName(licenseInfo.get().getLicenseType()));
    }

    @Transactional(readOnly = true)
    @GetMapping("/licenses/license/license-ticket-count-info")
    public ResponseEntity<HashMap<String, Object>> getLicenseTicketCountInfo() {
        List<ContractTicketCountInfoDto> contractTicketCountInfoDtos
                = licenseInfoJpaRepo.findContractTicketCountInfo(
                TypeDefine.Status.ISSUE_LICENSE,
                TypeDefine.Status.LICENSE_IN_USE,
                TypeDefine.LicenseType.COMMERCIAL,
                TypeDefine.LicenseType.CLOUD_INSTANCE);

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", ResponseService.CommonResponse.SUCCESS.getCode());
        hashMapResp.put("msg", ResponseService.CommonResponse.SUCCESS.getMsg());
        hashMapResp.put("contractTicketCountInfoList", contractTicketCountInfoDtos);

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping("/common-agent/licenses/license/state")
    public ResponseEntity<HashMap<String, Object>> getCommonLicenseState(
            @RequestParam(value = "license-id", required = true) String licenseId
            , @RequestParam(value = "node-id", required = true) String nodeId)
            throws ParseException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException, IOException {
        Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(licenseId);

        if (!licenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        if (licenseInfo.get().getComponentName().contentEquals(TypeDefine.ProductName.WAPPLES.getOrgName())) {
            licenseService.commonLicenseStatusChange();
        }

        CommonLicenseStateDto.CommonLicenseWapplesDtoResp commonLicenseWapplesDtoResp
                = new CommonLicenseStateDto.CommonLicenseWapplesDtoResp();

        Optional<LicenseInfo> licenseInfo2 = licenseInfoJpaRepo.findBySerialNo(licenseId);

        commonLicenseWapplesDtoResp.setLicenseStatus(licenseInfo2.get().getStatus().getName());

        if (licenseInfo2.get().getStatus() == TypeDefine.Status.ISSUE_LICENSE ||
                licenseInfo2.get().getStatus() == TypeDefine.Status.LICENSE_IN_USE) {

            LicenseInfo licenseInfo1 = licenseInfo2.get();
            licenseInfo1.setLastUsageTime(new Date());
            licenseInfoJpaRepo.save(licenseInfo1);

            commonLicenseWapplesDtoResp.setLicenseType(licenseInfo2.get().getLicenseType().getName());

            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
            Date nowDate = sdfDate.parse(sdfDate.format(new Date()));
            Date nowDateTime = new Date();

//        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //??? ??????
//        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //??? ??????
//        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //??? ??????
//        long diffDays = diffSec / (24*60*60); //????????? ??????

            long diffDays = (licenseInfo2.get().getLicenseExpiredDate().getTime() - nowDate.getTime()) / (24 * 60 * 60 * 1000);
            if (licenseInfo2.get().getLicenseExpiredDate().compareTo(nowDate) > 0) {
                // ???????????? ??????????????? 15??? ??????????????? true??? ????????????.
                if (diffDays <= 15)
                    commonLicenseWapplesDtoResp.setShouldRenewCert(true);
                else
                    commonLicenseWapplesDtoResp.setShouldRenewCert(false);
            } else if (licenseInfo2.get().getLicenseExpiredDate().compareTo(nowDate) == 0) {
                commonLicenseWapplesDtoResp.setShouldRenewCert(false);
            } else if (licenseInfo2.get().getLicenseExpiredDate().compareTo(nowDate) < 0) {
                commonLicenseWapplesDtoResp.setShouldRenewCert(false);
                commonLicenseWapplesDtoResp.setLicenseStatus(TypeDefine.Status.UNUSED_LICENSE.getName());
            }

            if (!nodeId.contentEquals(licenseInfo2.get().getNodeId())) {
                commonLicenseWapplesDtoResp.setLicenseStatus(TypeDefine.Status.DUPLICATED.getName());
            }

        } else {
            commonLicenseWapplesDtoResp.setLicenseType(licenseInfo2.get().getLicenseType().getName());
            commonLicenseWapplesDtoResp.setShouldRenewCert(false);
        }

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", ResponseService.CommonResponse.SUCCESS.getCode());
        hashMapResp.put("msg", ResponseService.CommonResponse.SUCCESS.getMsg());
        hashMapResp.put("status", commonLicenseWapplesDtoResp.getLicenseStatus());
        hashMapResp.put("licenseType", commonLicenseWapplesDtoResp.getLicenseType());
        hashMapResp.put("shouldRenewCert", commonLicenseWapplesDtoResp.getShouldRenewCert());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @PutMapping("/common-agent/licenses/license/state")
    public ResponseEntity<HashMap<String, Object>> putCommonLicenseState(@RequestBody Map<String, Object> param) {
        String licenseId = param.get("licenseId").toString();
        String isCertValid = param.get("isCertValid").toString();

        Optional<LicenseInfo> licenseInfo = licenseInfoJpaRepo.findBySerialNo(licenseId);

        if (!licenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        if (licenseInfo.get().getStatus().getName().contentEquals(TypeDefine.Status.ISSUE_LICENSE.getName())) {
            if (isCertValid.toLowerCase().contentEquals("true")) {
                LicenseInfo licenseInfo1 = licenseInfo.get();
                licenseInfo1.setStatus(TypeDefine.Status.LICENSE_IN_USE);
                licenseInfo1.setLastUsageTime(new Date());
                licenseInfoJpaRepo.save(licenseInfo1);
            }
        } else if (licenseInfo.get().getStatus().getName().contentEquals(TypeDefine.Status.LICENSE_IN_USE.getName())) {
            if (!isCertValid.toLowerCase().contentEquals("true")) {
                LicenseInfo licenseInfo1 = licenseInfo.get();
                licenseInfo1.setStatus(TypeDefine.Status.ISSUE_LICENSE);
                licenseInfo1.setLastUsageTime(new Date());
                licenseInfoJpaRepo.save(licenseInfo1);
            }
        }

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", ResponseService.CommonResponse.SUCCESS.getCode());
        hashMapResp.put("msg", ResponseService.CommonResponse.SUCCESS.getMsg());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @PutMapping("/common-agent/licenses/monitor/interval")
    public ResponseEntity<HashMap<String, Object>> setupTimeUnit(@RequestBody Map<String, Object> param) throws IOException {
        String line = null;
        String repLine = null;

        if (param == null) {
            throw new NullPointerException();
        }

        String healthCheckInterval = param.get("interval").toString();
        String healthCheckIntervalFullString = "health-check-interval=" + healthCheckInterval;

        ApplicationHome home = new ApplicationHome(this.getClass());
        File file = new File(
                home.getDir() + File.separator + "conf" + File.separator + "license");
        file.mkdir();

        File confFile = new File(
                home.getDir() + File.separator + "conf" + File.separator + "license" + File.separator + "setup.conf");

        boolean isExists = confFile.exists();
        if (!isExists) {
            confFile.createNewFile();

            OutputStream output = new FileOutputStream(
                    home.getDir() + File.separator + "conf" + File.separator + "license" + File.separator + "setup.conf");

            output.write(healthCheckIntervalFullString.getBytes());
            output.close();
        } else {
            BufferedReader br = new BufferedReader(new FileReader(confFile));

            while ((line = br.readLine()) != null) {
                StringTokenizer s = new StringTokenizer(line);

                if (repLine == null)
                    repLine = "";

                if (s.nextToken("=").equals("health-check-interval")) {
                    String orgHealthCheckInterval = s.nextElement().toString();
                    repLine = line.replaceAll("health-check-interval=" + orgHealthCheckInterval,
                            "health-check-interval=" + healthCheckInterval)
                            + "\n" + repLine;
                } else {
                    repLine = line + "\n" + repLine;
                }
            }
            br.close();

            OutputStream output = new FileOutputStream(
                    home.getDir() + File.separator + "conf" + File.separator + "license" + File.separator + "setup.conf");
            if (repLine == null || repLine.isEmpty() || repLine.contentEquals("")) {
                output.write(healthCheckIntervalFullString.getBytes());
            } else {
                output.write(repLine.getBytes());
            }
            output.close();
        }

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", ResponseService.CommonResponse.SUCCESS.getCode());
        hashMapResp.put("msg", ResponseService.CommonResponse.SUCCESS.getMsg());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping("/common-agent/licenses/monitor/interval")
    public ResponseEntity<HashMap<String, Object>> getTimeUnit() throws IOException {
        int repLine = licenseService.readTimeUnitFromConfFile();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", ResponseService.CommonResponse.SUCCESS.getCode());
        hashMapResp.put("msg", ResponseService.CommonResponse.SUCCESS.getMsg());
        hashMapResp.put("interval", repLine);

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping("/common-agent/licenses/license/expired-data-search")
    public CommonResult getCommonLicenseExpiredDataSearch() throws InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, ParseException, IOException {
        licenseService.commonLicenseStatusChange();
        return responseService.getSuccessResult();
    }

    @PutMapping("common-agent/licenses/license/product-info")
    public CommonResult updateProductInfoOfLicenseInfo(@RequestBody LicenseInfoDto licenseInfoDto) {
        Optional<LicenseInfo> tempLicenseInfo = licenseInfoJpaRepo.findBySerialNo(licenseInfoDto.getSerialNo());

        if (!tempLicenseInfo.isPresent()) {
            throw new SearchSerialNoException();
        }

        LicenseInfoDto tempLicenseInfoDto = LicenseMapper.INSTANCE.entityToLicenseInfoDto(tempLicenseInfo.get());
        tempLicenseInfoDto.setComponentVersion(licenseInfoDto.getComponentVersion());

        licenseInfoJpaRepo.save(LicenseMapper.INSTANCE.dtoToEntity(tempLicenseInfoDto));

        logModuleService.SavePolicyLog(LogLevelType.INFO,
                "[Licenses] " + tempLicenseInfoDto.getSerialNo() + " ComponentVersion changed to " +
                        tempLicenseInfoDto.getComponentVersion());

        return responseService.getSuccessResult();
    }
}
