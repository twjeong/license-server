package com.penta.ps.license.license.controller.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.penta.ps.license.license.dto.contract.*;
import com.penta.ps.license.license.exception.contract.ContractInputValueCheckException;
import com.penta.ps.license.license.exception.common.NotFoundDataException;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.contract.ContractInfoJsonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping
public class ContractInfoJsonController {
    private final ContractInfoJsonService contractInfoJsonService;

    @GetMapping(value = "/contracts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getAllContractInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "10") Integer recordsPerPage) throws SSLException, JsonProcessingException {

        // 전체조회
        ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllContractInfoDtoResp
                = contractInfoJsonService.findAllPage(currentPageNo, recordsPerPage);

        if (findAllContractInfoDtoResp == null || findAllContractInfoDtoResp.getFindAllContractInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", findAllContractInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", findAllContractInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("pageTotalSize", findAllContractInfoDtoResp.getPageDto().getPageTotalSize());
        hashMapResp.put("contractInfoList", findAllContractInfoDtoResp.getFindAllContractInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/specific-contracts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getAllSpecificContractInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "10") Integer recordsPerPage,
            @RequestParam(name = "cust-nm", required = false) String custNm,
            @RequestParam(name = "order-num", required = false) String orderNum,
            @RequestParam(name = "lis-pro-nm", required = false) String lisProNm) throws JsonProcessingException {

        ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllContractInfoDtoResp = new ContractInfoJsonDto.FindAllPageContractInfoDtoResp();

        // 값없으면 전체조회
        if ((custNm == null || custNm.isEmpty()) &&
                (orderNum == null || orderNum.isEmpty()) &&
                (lisProNm == null || lisProNm.isEmpty())) {
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPage(currentPageNo, recordsPerPage);
        } else if (!(custNm == null || custNm.isEmpty()) &&
                (orderNum == null || orderNum.isEmpty()) &&
                (lisProNm == null || lisProNm.isEmpty())) {
            // custNm 기반 조회
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByCustNm(currentPageNo, recordsPerPage, custNm);

        } else if ((custNm == null || custNm.isEmpty()) &&
                !(orderNum == null || orderNum.isEmpty()) &&
                (lisProNm == null || lisProNm.isEmpty())) {
            // 납품계약번호 기반 조회
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByOrderNum(currentPageNo, recordsPerPage, orderNum);
        } else if ((custNm == null || custNm.isEmpty()) &&
                (orderNum == null || orderNum.isEmpty()) &&
                !(lisProNm == null || lisProNm.isEmpty())) {
            // 컴퍼넌트명 기반 조회
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByLisProNm(currentPageNo, recordsPerPage, lisProNm);
        } else if (!(custNm == null || custNm.isEmpty()) &&
                !(orderNum == null || orderNum.isEmpty()) &&
                (lisProNm == null || lisProNm.isEmpty())) {
            // custNm And 납품계약번호 기반조회
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByCustnmAndOrderNum(currentPageNo, recordsPerPage, custNm, orderNum);
        } else if (!(custNm == null || custNm.isEmpty()) &&
                (orderNum == null || orderNum.isEmpty()) &&
                !(lisProNm == null || lisProNm.isEmpty())) {
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByCustNameAndLisProNm(currentPageNo, recordsPerPage, custNm, lisProNm);
        } else if ((custNm == null || custNm.isEmpty()) &&
                !(orderNum == null || orderNum.isEmpty()) &&
                !(lisProNm == null || lisProNm.isEmpty())) {
            findAllContractInfoDtoResp = contractInfoJsonService.findAllPageByOrderNumAndLisProNm(currentPageNo, recordsPerPage, orderNum, lisProNm);
        } else {
            throw new ContractInputValueCheckException();
        }

        if (findAllContractInfoDtoResp == null || findAllContractInfoDtoResp.getFindAllContractInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", findAllContractInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", findAllContractInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("pageTotalSize", findAllContractInfoDtoResp.getPageDto().getPageTotalSize());
        hashMapResp.put("contractInfoList", findAllContractInfoDtoResp.getFindAllContractInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contract-ext-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractExtendInfo(@RequestParam(name = "key-idx", required = true) Integer keyIdx,
                                                                         @RequestParam(name = "contract-file-name", required = true) String contractFileName,
                                                                         @RequestParam(name = "component-version", required = false) String componentVersion) throws JsonProcessingException {
        ContractExtendedInfoDto.ContractExtendedInfo contractExtendedInfo
                = contractInfoJsonService.findAllByKeyIdxAndContractFileName(keyIdx, contractFileName, componentVersion);

        if (contractExtendedInfo.getContractExtendedInfoDto() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", contractExtendedInfo.getResultDto().getResult());
        hashMapResp.put("msg", contractExtendedInfo.getResultDto().getMsg());
        hashMapResp.put("contractExtenedInfo", contractExtendedInfo.getContractExtendedInfoDto());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/license/contract-file-group-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractFileGroupInfo() throws SSLException {

        ContractInfoJsonDto.FindAllContractFileGroupInfoDtoResp findAllContractFileGroupInfoDtoResp
                = contractInfoJsonService.findContractFileGroupInfo();

        if (findAllContractFileGroupInfoDtoResp.getContractFileGroupInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();
        HashMap<String, Object> hashMapDetailResp = new HashMap<String, Object>();

        hashMapResp.put("result", findAllContractFileGroupInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", findAllContractFileGroupInfoDtoResp.getResultDto().getMsg());

        List<ContractFileGroupInfoDto> tempFileGroupInfoDtos = new ArrayList<>();
        for (ContractFileGroupInfoDto contractFileGroupInfoDto : findAllContractFileGroupInfoDtoResp.getContractFileGroupInfoDtos()) {
            if (!hashMapDetailResp.containsKey(contractFileGroupInfoDto.getContractFileName())) {
                tempFileGroupInfoDtos = new ArrayList<>();
                hashMapDetailResp.put(contractFileGroupInfoDto.getContractFileName(), new Object());
            }
            tempFileGroupInfoDtos.add(ContractFileGroupInfoDto.builder()
                    .lisProNm(contractFileGroupInfoDto.getLisProNm())
                    .lisTypeOcUp(contractFileGroupInfoDto.getLisTypeOcUp())
                    .resultCount(contractFileGroupInfoDto.getResultCount())
                    .build());

            hashMapDetailResp.put(contractFileGroupInfoDto.getContractFileName(), tempFileGroupInfoDtos);
        }

        hashMapResp.put("contractFileGroupList", hashMapDetailResp);

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/license/contract-file-summary-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractFilesummaryInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "10") Integer recordsPerPage,
            @RequestParam(name = "contract-file-nm", required = false) String contractFileNm) throws JsonProcessingException, ParseException {

        ContractInfoJsonDto.FindContractFileSummaryInfoDtoResp FindContractFileSummaryInfoDtoResp
                = contractInfoJsonService.findContractFileSummaryInfo(currentPageNo, recordsPerPage, contractFileNm);

        if (FindContractFileSummaryInfoDtoResp.getContractFileSummaryInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", FindContractFileSummaryInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", FindContractFileSummaryInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("pageTotalSize", FindContractFileSummaryInfoDtoResp.getPageDto().getPageTotalSize());
        hashMapResp.put("contractSummaryInfoList", FindContractFileSummaryInfoDtoResp.getContractFileSummaryInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/license/contract-file-detail-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractFileDetailInfo(
            @RequestParam(name = "contract-file-nm", required = true) String contractFileNm,
            @RequestParam(name = "key-idx", required = true) Integer keyIdx) throws SSLException, JsonProcessingException {

        ContractInfoJsonDto.FindContractInfoDtoResp findContractInfoDtoResp
                = contractInfoJsonService.findContractFileDetailInfo(contractFileNm, keyIdx);

        if (findContractInfoDtoResp.getFindContractInfoDto() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<>();

        hashMapResp.put("result", findContractInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", findContractInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("contractDetailInfo", findContractInfoDtoResp.getFindContractInfoDto());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/license/contract-register-group-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractRegisterGroupInfo() throws JsonProcessingException, ParseException {
        ContractRegisterGroupInfoDto.ContractRegisterGroupInfoDtoResp contractRegisterGroupInfoDtoResp
                = contractInfoJsonService.findContractRegisterGroupInfo();

        if (contractRegisterGroupInfoDtoResp.getContractRegisterGroupInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", contractRegisterGroupInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", contractRegisterGroupInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("contractRegisterGroupInfoList", contractRegisterGroupInfoDtoResp.getContractRegisterGroupInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/license/contract-register-group-detail-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractRegisterGroupDetailInfo(
            @ModelAttribute ContractRegisterGroupDetailInfoDto contractRegisterGroupDetailInfoDto) throws JsonProcessingException {
        ContractInfoJsonDto.FindAllContractInfoDtoResp findAllContractInfoDtoResp
                = contractInfoJsonService.findContractRegisterGroupDetailInfo(contractRegisterGroupDetailInfoDto);

        if (findAllContractInfoDtoResp.getFindAllContractInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", findAllContractInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", findAllContractInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("contractRegisterGroupDetailInfoList", findAllContractInfoDtoResp.getFindAllContractInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/contract/contract-custname-group-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractCustNameGroupInfo() throws SSLException, JsonProcessingException {
        ContractInfoJsonDto.FindContractCustNameGroupInfoResp findContractCustNameGroupInfoResp
                = contractInfoJsonService.FindGroupByCustNmAndLisProNm();

        if (findContractCustNameGroupInfoResp.getContractCustNameGroupInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();
        HashMap<String, Object> hashMapDetailResp = new HashMap<String, Object>();

        hashMapResp.put("result", findContractCustNameGroupInfoResp.getResultDto().getResult());
        hashMapResp.put("msg", findContractCustNameGroupInfoResp.getResultDto().getMsg());

        List<String> lisProNmList = new ArrayList<>();
        for (ContractCustNameGroupInfoDto contractCustNameGroupInfoDto : findContractCustNameGroupInfoResp.getContractCustNameGroupInfoDtos()) {
            if (!hashMapDetailResp.containsKey(contractCustNameGroupInfoDto.getCustName())) {
                lisProNmList = new ArrayList<>();
                hashMapDetailResp.put(contractCustNameGroupInfoDto.getCustName(), new Object());
            }
            lisProNmList.add(contractCustNameGroupInfoDto.getLisProNm());
            hashMapDetailResp.put(contractCustNameGroupInfoDto.getCustName(), lisProNmList);
        }

        hashMapResp.put("contractCustNameGroupInfo", hashMapDetailResp);

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/contract/contract-custname-summary-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractCustNameSummaryInfo(@RequestParam(name = "cust-nm", required = true) String custNm) throws SSLException, JsonProcessingException {
        ContractInfoJsonDto.FindContractCustNameGroupInfoResp findContractCustNameGroupInfoResp
                = contractInfoJsonService.FindGroupByLisProNmAndLisTypeOcUp(custNm);

        if (findContractCustNameGroupInfoResp.getContractCustNameGroupInfoDtos() == null)
            throw new NotFoundDataException();

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", findContractCustNameGroupInfoResp.getResultDto().getResult());
        hashMapResp.put("msg", findContractCustNameGroupInfoResp.getResultDto().getMsg());
        hashMapResp.put("contractCustNameSummaryInfoList", findContractCustNameGroupInfoResp.getContractCustNameGroupInfoDtos());

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    @GetMapping(value = "/contracts/contract/contract-custname-detail-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractCustNameDetailInfo(
            @RequestParam(name = "current-page-no", required = false, defaultValue = "1") Integer currentPageNo,
            @RequestParam(name = "records-per-page", required = false, defaultValue = "1") Integer recordsPerPage,
            @RequestParam(name = "cust-nm", required = false) String custNm,
            @RequestParam(name = "lis-pro-nm", required = false) String lisProNm) throws JsonProcessingException, ParseException {

        ContractInfoJsonDto.FindContractCustNameDetailInfoResp findContractCustNameDetailInfoResp
                = contractInfoJsonService.findDetailInfoByCustNameAndLisProNm(currentPageNo,
                recordsPerPage,
                custNm,
                lisProNm);

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", findContractCustNameDetailInfoResp.getResultDto().getResult());
        hashMapResp.put("msg", findContractCustNameDetailInfoResp.getResultDto().getMsg());
        hashMapResp.put("pageTotalSize", findContractCustNameDetailInfoResp.getPageDto().getPageTotalSize());
        hashMapResp.put("contractCustNameDetailInfoList", findContractCustNameDetailInfoResp.getContractCustNameDetailInfoDtos());

        return new ResponseEntity<HashMap<String, Object>>(hashMapResp, HttpStatus.OK);
    }

    /*
    // 미사용
    @GetMapping(value = "/contracts/contract/contract-ticket-count-info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<HashMap<String, Object>> getContractTicketCountInfo() {
        ContractTicketCountInfoDto.ContractTicketCountInfoDtoResp contractTicketCountInfoDtoResp
                = contractInfoJsonService.findContractTicketCountInfo();

        HashMap<String, Object> hashMapResp = new HashMap<String, Object>();

        hashMapResp.put("result", contractTicketCountInfoDtoResp.getResultDto().getResult());
        hashMapResp.put("msg", contractTicketCountInfoDtoResp.getResultDto().getMsg());
        hashMapResp.put("contractTicketCountInfoList", contractTicketCountInfoDtoResp.getContractTicketCountInfoDtos());

        return new ResponseEntity<>(hashMapResp, HttpStatus.OK);
    }
     */

    @PostMapping(value = "/contracts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContractInfoJsonDto.ResultDto> saveContractInfo(
            @RequestBody List<ContractInfoJsonDto.SaveContractInfoDtoReq> saveContractInfoDtoReqs) throws SSLException {
        return new ResponseEntity<>(contractInfoJsonService.saveContractInfo(saveContractInfoDtoReqs),
                HttpStatus.OK);
    }

    @PutMapping(value = "/contracts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContractInfoJsonDto.ResultDto> updateContractInfo(
            @RequestBody List<ContractInfoJsonDto.SaveContractInfoDtoReq> updateContractInfoDtoReqs) throws SSLException {
        return new ResponseEntity<>(contractInfoJsonService.updateContractInfo(updateContractInfoDtoReqs),
                HttpStatus.OK);
    }

    @PutMapping(value = "/contracts/license-status", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContractInfoJsonDto.ResultDto> updateLicenseStatus(
            @RequestBody ContractInfoJsonDto.UpdateLicenseStatusReq updateLicenseStatusReq) throws JsonProcessingException {
        return new ResponseEntity<>(contractInfoJsonService.updateLicenseStatus(updateLicenseStatusReq),
                HttpStatus.OK);
    }

    @DeleteMapping(value = "/contracts", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ContractInfoJsonDto.ResultDto> deleteContractInfo(@RequestBody List<ContractInfoJsonDto.ContractInfoJsonDataDto> deleteContractInfoDtoReqs) throws SSLException {
        return new ResponseEntity<>(contractInfoJsonService.deleteContractInfo(deleteContractInfoDtoReqs),
                HttpStatus.OK);
    }
}
