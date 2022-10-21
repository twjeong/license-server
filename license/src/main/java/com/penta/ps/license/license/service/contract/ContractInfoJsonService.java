package com.penta.ps.license.license.service.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.ps.license.license.dto.contract.*;
import com.penta.ps.license.license.dto.license.LicenseInfoDto;
import com.penta.ps.license.license.entity.ContractInfoJsonEntity;
import com.penta.ps.license.license.exception.common.NotFoundDataException;
import com.penta.ps.license.license.exception.contract.*;
import com.penta.ps.license.license.mapper.ContractInfoJsonMapper;
import com.penta.ps.license.license.mapper.LicenseMapper;
import com.penta.ps.license.license.repository.ContractInfoJsonRepository;
import com.penta.ps.license.license.repository.LicenseInfoJpaRepo;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ContractInfoJsonService {
    private final LicenseInfoJpaRepo licenseInfoJpaRepo;
    private final ContractInfoJsonRepository contractInfoJsonRepository;
    private final ContractExtendedInfoService contractExtendedInfoService;
    private final LogModuleService logModuleService;

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPage(Integer currentPageNo,
                                                                          Integer recordsPerPage) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoJsonEntities
                = contractInfoJsonRepository.findAll(PageRequest.of(currentPageNo - 1, recordsPerPage));

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoJsonEntities.getContent());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoJsonEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByCustNm(Integer currentPageNo,
                                                                                  Integer recordsPerPage,
                                                                                  String custNm) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByCustNm(
                        PageRequest.of(currentPageNo - 1, recordsPerPage), custNm);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByOrderNum(Integer currentPageNo,
                                                                                    Integer recordsPerPage,
                                                                                    String orderNum) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByOrderNum(PageRequest.of(currentPageNo - 1, recordsPerPage), orderNum);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());


        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByLisProNm(Integer currentPageNo,
                                                                                    Integer recordsPerPage,
                                                                                    String lisProNm) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByLisProNm(PageRequest.of(currentPageNo - 1, recordsPerPage), lisProNm);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    //@Transactional(readOnly = true)
    public ContractExtendedInfoDto.ContractExtendedInfo findAllByKeyIdxAndContractFileName(Integer keyIdx,
                                                                                           String contractFileName,
                                                                                           String componentVersion) throws JsonProcessingException {
        ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto
                = ContractInfoJsonMapper.INSTANCE.EntityToDto(
                contractInfoJsonRepository.findByContractFileNameAndKeyIdx(contractFileName, keyIdx));

        if (innerContractInfoJsonDto == null)
            throw new NotFoundDataException();

        ContractExtendedInfoDto contractExtendedInfoDto = new ContractExtendedInfoDto();
        contractExtendedInfoService.setContractExtendInfo(componentVersion, innerContractInfoJsonDto, contractExtendedInfoDto);

        return new ContractExtendedInfoDto.ContractExtendedInfo(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                contractExtendedInfoDto);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByCustnmAndOrderNum(Integer currentPageNo,
                                                                                             Integer recordsPerPage,
                                                                                             String custNm,
                                                                                             String orderNum) throws JsonProcessingException {

        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByCustNmAndOrderNum(PageRequest.of(currentPageNo - 1, recordsPerPage), custNm, orderNum);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());


        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();

        ObjectMapper objectMapper2 = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper2.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByCustNameAndLisProNm(Integer currentPageNo,
                                                                                               Integer recordsPerPage,
                                                                                               String custName,
                                                                                               String lisProNm) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByCustNmAndLisProNm(
                        PageRequest.of(currentPageNo - 1, recordsPerPage),
                        custName,
                        lisProNm);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());


        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();

        ObjectMapper objectMapper2 = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper2.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllPageContractInfoDtoResp findAllPageByOrderNumAndLisProNm(Integer currentPageNo,
                                                                                               Integer recordsPerPage,
                                                                                               String orderNum,
                                                                                               String lisProNm) throws JsonProcessingException {
        Page<ContractInfoJsonEntity> contractInfoEntities =
                contractInfoJsonRepository.findByOrderNumAndLisProNm(
                        PageRequest.of(currentPageNo - 1, recordsPerPage),
                        orderNum,
                        lisProNm);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());


        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();

        ObjectMapper objectMapper2 = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper2.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        return new ContractInfoJsonDto.FindAllPageContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractInfoJsonDataDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllContractFileGroupInfoDtoResp findContractFileGroupInfo() {
        return new ContractInfoJsonDto.FindAllContractFileGroupInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                contractInfoJsonRepository.findGroupByContractFileNameAndLisProNmAndLisTypeOcUp());
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindContractFileSummaryInfoDtoResp findContractFileSummaryInfo(Integer currentPageNo,
                                                                                              Integer recordsPerPage,
                                                                                              String contractFileName) throws JsonProcessingException, ParseException {
        Page<ContractInfoJsonEntity> contractInfoEntities;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        if (contractFileName == null) {
            contractInfoEntities =
                    contractInfoJsonRepository.findAll(
                            PageRequest.of(currentPageNo - 1, recordsPerPage));
        } else {
            contractInfoEntities =
                    contractInfoJsonRepository.findByContractFileName(
                            PageRequest.of(currentPageNo - 1, recordsPerPage),
                            contractFileName);
        }

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoEntities.getContent());

        ObjectMapper objectMapper = new ObjectMapper();
        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        List<ContractInfoJsonDto.ContractFileSummaryInfoDto> contractFileSummaryInfoDtos = new ArrayList<>();

        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            contractFileSummaryInfoDtos.add(ContractInfoJsonDto.ContractFileSummaryInfoDto
                    .builder()
                    .contractFileName(contractInfoJsonDataDto.getContractFileName())
                    .lisSDate(sdf.parse(contractInfoJsonDataDto.getLisSDate()))
                    .lisEDate(sdf.parse(contractInfoJsonDataDto.getLisEDate()))
                    .insSDt(sdf.parse(contractInfoJsonDataDto.getInsSDt()))
                    .insEDt(sdf.parse(contractInfoJsonDataDto.getInsEDt()))
                    .custNm(contractInfoJsonDataDto.getCustNm())
                    .keyIdx(contractInfoJsonDataDto.getKeyIdx())
                    .lisProNm(contractInfoJsonDataDto.getLisProNm())
                    .lisTypeOcUp(contractInfoJsonDataDto.getLisTypeOcUp())
                    .orderNum(contractInfoJsonDataDto.getOrderNum())
                    .build());
        }

        return new ContractInfoJsonDto.FindContractFileSummaryInfoDtoResp(
                ContractInfoJsonDto.ResultDto
                        .builder()
                        .result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name())
                        .build(),
                ContractInfoJsonDto.PageDto
                        .builder()
                        .pageTotalSize((long) contractInfoEntities.getTotalPages())
                        .build(),
                contractFileSummaryInfoDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindContractInfoDtoResp findContractFileDetailInfo(String contractFileName,
                                                                                  Integer keyIdx) throws JsonProcessingException {
        ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto
                = ContractInfoJsonMapper.INSTANCE.EntityToDto(
                contractInfoJsonRepository.findByContractFileNameAndKeyIdx(
                        contractFileName,
                        keyIdx));

        if (innerContractInfoJsonDto == null)
            throw new NotFoundDataException();

        ObjectMapper objectMapper = new ObjectMapper();
        ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto
                = objectMapper.readValue(innerContractInfoJsonDto.getContractInfoJson(),
                ContractInfoJsonDto.ContractInfoJsonDataDto.class);

        contractInfoJsonDataDto.setLisSDate(LocalDate.parse(contractInfoJsonDataDto.getLisSDate()).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        contractInfoJsonDataDto.setLisEDate(LocalDate.parse(contractInfoJsonDataDto.getLisEDate()).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        contractInfoJsonDataDto.setInsSDt(LocalDate.parse(contractInfoJsonDataDto.getInsSDt()).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        contractInfoJsonDataDto.setInsEDt(LocalDate.parse(contractInfoJsonDataDto.getInsEDt()).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        contractInfoJsonDataDto.setLicenseStatus(null);

        return new ContractInfoJsonDto.FindContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                contractInfoJsonDataDto);
    }

    @Transactional(readOnly = true)
    public ContractRegisterGroupInfoDto.ContractRegisterGroupInfoDtoResp findContractRegisterGroupInfo() throws JsonProcessingException, ParseException {
        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoJsonRepository.findAll());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        HashMap<String, String> contractInfoJsonDataGroups = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(innerContractInfoJsonDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        long allCount = 0;
        long availableCount = 0;
        long expiredCount = 0;
        long usedCount = 0;
        int idx = 0;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String now_dt = format.format(now);

        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            String strContractDataGroup
                    = contractInfoJsonDataDto.getKmsPriKey() + "," +
                    contractInfoJsonDataDto.getEndUserId() + "," +
                    contractInfoJsonDataDto.getEstiType() + "," +
                    innerContractInfoJsonDtos.get(idx).getOrderNum() + "," +
                    format.format(innerContractInfoJsonDtos.get(idx).getLisStartDate()) + "," +
                    format.format(innerContractInfoJsonDtos.get(idx).getLisEndDate()) + "," +
                    innerContractInfoJsonDtos.get(idx).getLisProNm() + "," +
                    contractInfoJsonDataDto.getKmsId() + "," +
                    contractInfoJsonDataDto.getHwSn() + "," +
                    innerContractInfoJsonDtos.get(idx).getCoreCount() + "," +
                    contractInfoJsonDataDto.getLisVerUp() + "," +
                    innerContractInfoJsonDtos.get(idx).getLisTypeOcUp() + "," +
                    contractInfoJsonDataDto.getCloudSp() + "," +
                    contractInfoJsonDataDto.getCloudCsi() + "," +
                    contractInfoJsonDataDto.getCloudSgn() + "," +
                    contractInfoJsonDataDto.getInstanceNm() + "," +
                    contractInfoJsonDataDto.getSymApiYn() + "," +
                    contractInfoJsonDataDto.getPubApiYn() + "," +
                    contractInfoJsonDataDto.getKewinApiYn() + "," +
                    contractInfoJsonDataDto.getApiYn() + "," +
                    contractInfoJsonDataDto.getKmsYn() + "," +
                    contractInfoJsonDataDto.getOsYn() + "," +
                    contractInfoJsonDataDto.getSiteNm() + "," +
                    contractInfoJsonDataDto.getLisOsType() + "," +
                    contractInfoJsonDataDto.getKmYn() + "," +
                    contractInfoJsonDataDto.getFileYn() + "," +
                    contractInfoJsonDataDto.getHashYn() + "," +
                    contractInfoJsonDataDto.getKmsEnc() + "," +
                    contractInfoJsonDataDto.getKmsPubKey() + "," +
                    contractInfoJsonDataDto.getWebCnt() + "," +
                    contractInfoJsonDataDto.getLisExpAct() + "," +
                    innerContractInfoJsonDtos.get(idx).getCustNm();

            if (contractInfoJsonDataGroups.containsKey(strContractDataGroup)) {
                String[] valueData = contractInfoJsonDataGroups.get(strContractDataGroup).split(",");

                allCount = Long.parseLong(valueData[0]);
                availableCount = Long.parseLong(valueData[1]);
                expiredCount = Long.parseLong(valueData[2]);
                usedCount = Long.parseLong(valueData[3]);

                allCount++;
                if ((innerContractInfoJsonDtos.get(idx).getLisStartDate().toString().compareTo(now_dt) > 0) ||
                        (innerContractInfoJsonDtos.get(idx).getLisEndDate().toString().compareTo(now_dt) < 0)) {
                    expiredCount++;
                } else if (innerContractInfoJsonDtos.get(idx).getLisStartDate().toString().compareTo(now_dt) <= 0 &&
                        innerContractInfoJsonDtos.get(idx).getLisEndDate().toString().compareTo(now_dt) >= 0) {
                    if (innerContractInfoJsonDtos.get(idx).getLicenseStatus().contentEquals("0")) {
                        availableCount++;
                    } else if (innerContractInfoJsonDtos.get(idx).getLicenseStatus().contentEquals("1")) {
                        usedCount++;
                    }
                }
            } else {
                allCount = 1;
                if (innerContractInfoJsonDtos.get(idx).getLisStartDate().toString().compareTo(now_dt) > 0 ||
                        innerContractInfoJsonDtos.get(idx).getLisEndDate().toString().compareTo(now_dt) < 0) {
                    expiredCount = 1;
                    availableCount = 0;
                    usedCount = 0;
                } else if (innerContractInfoJsonDtos.get(idx).getLisStartDate().toString().compareTo(now_dt) <= 0 &&
                        innerContractInfoJsonDtos.get(idx).getLisEndDate().toString().compareTo(now_dt) >= 0) {
                    if (innerContractInfoJsonDtos.get(idx).getLicenseStatus().contentEquals("0")) {
                        expiredCount = 0;
                        availableCount = 1;
                        usedCount = 0;
                    } else if (innerContractInfoJsonDtos.get(idx).getLicenseStatus().contentEquals("1")) {
                        expiredCount = 0;
                        availableCount = 0;
                        usedCount = 1;
                    }
                }
            }
            contractInfoJsonDataGroups.put(strContractDataGroup,
                    allCount + "," +
                            availableCount + "," +
                            expiredCount + "," +
                            usedCount);
            idx++;
        }

        List<ContractRegisterGroupInfoDto> contractRegisterGroupInfoDtos = new ArrayList<>();

        for (String key : contractInfoJsonDataGroups.keySet()) {
            String value = contractInfoJsonDataGroups.get(key);
            String[] keyData = key.split(",");
            String[] valueData = value.split(",");

            ContractRegisterGroupInfoDto contractRegisterGroupInfoDto = new ContractRegisterGroupInfoDto();

            contractRegisterGroupInfoDto.setCustNm(keyData[31]);
            contractRegisterGroupInfoDto.setEndUserId(Integer.parseInt(keyData[1]));
            contractRegisterGroupInfoDto.setEstiType(keyData[2]);
            contractRegisterGroupInfoDto.setOrderNum(keyData[3]);
            contractRegisterGroupInfoDto.setStartDate(keyData[4]);
            contractRegisterGroupInfoDto.setEndDate(keyData[5]);
            contractRegisterGroupInfoDto.setLisProNm(keyData[6]);
            contractRegisterGroupInfoDto.setKmsId(keyData[7]);
            contractRegisterGroupInfoDto.setHwSn(keyData[8]);
            contractRegisterGroupInfoDto.setCpuCount(Integer.parseInt(keyData[9]));
            contractRegisterGroupInfoDto.setLisVerUp(keyData[10]);
            contractRegisterGroupInfoDto.setLisTypeOcUp(keyData[11]);
            contractRegisterGroupInfoDto.setCloudSp(keyData[12]);
            contractRegisterGroupInfoDto.setCloudCsi(keyData[13]);
            contractRegisterGroupInfoDto.setCloudSgn(keyData[14]);
            contractRegisterGroupInfoDto.setInstanceNm(keyData[15]);
            contractRegisterGroupInfoDto.setSymApiYn(keyData[16]);
            contractRegisterGroupInfoDto.setPubApiYn(keyData[17]);
            contractRegisterGroupInfoDto.setKewinApiYn(keyData[18]);
            contractRegisterGroupInfoDto.setApiYn(keyData[19]);
            contractRegisterGroupInfoDto.setKmsYn(keyData[20]);
            contractRegisterGroupInfoDto.setOsYn(keyData[21]);
            contractRegisterGroupInfoDto.setSiteNm(keyData[22]);
            contractRegisterGroupInfoDto.setLisOsType(keyData[23]);
            contractRegisterGroupInfoDto.setKmYn(keyData[24]);
            contractRegisterGroupInfoDto.setFileYn(keyData[25]);
            contractRegisterGroupInfoDto.setHashYn(keyData[26]);
            contractRegisterGroupInfoDto.setKmsEnc(keyData[27]);
            contractRegisterGroupInfoDto.setKmsPubKey(keyData[28]);
            contractRegisterGroupInfoDto.setKmsPriKey(keyData[0]);
            contractRegisterGroupInfoDto.setWebCnt(Integer.parseInt(keyData[29]));
            contractRegisterGroupInfoDto.setLisExpAct(Integer.parseInt(keyData[30]));

            contractRegisterGroupInfoDto.setAllCount(Long.parseLong(valueData[0]));
            contractRegisterGroupInfoDto.setAvailableCount(Long.parseLong(valueData[1]));
            contractRegisterGroupInfoDto.setExpiredCount(Long.parseLong(valueData[2]));
            contractRegisterGroupInfoDto.setUsedCount(Long.parseLong(valueData[3]));

            contractRegisterGroupInfoDtos.add(contractRegisterGroupInfoDto);
        }

        return new ContractRegisterGroupInfoDto.ContractRegisterGroupInfoDtoResp(
                ContractInfoJsonDto.ResultDto
                        .builder()
                        .result(ResultType.SUCCESS.ordinal())
                        .msg(ResultType.SUCCESS.name()).build(),
                contractRegisterGroupInfoDtos
        );
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindAllContractInfoDtoResp findContractRegisterGroupDetailInfo(
            ContractRegisterGroupDetailInfoDto contractRegisterGroupDetailInfoDto) throws JsonProcessingException {
        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoJsonRepository.findAll());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(innerContractInfoJsonDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<ContractInfoJsonDto.ContractInfoJsonDataDto> findAllContractInfoDtos = new ArrayList<>();

        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            if (contractInfoJsonDataDto.getCustNm().contentEquals(contractRegisterGroupDetailInfoDto.getCustNm()) &&
                    contractInfoJsonDataDto.getEndUserId().equals(contractRegisterGroupDetailInfoDto.getEndUserId()) &&
                    contractInfoJsonDataDto.getEstiType().contentEquals(contractRegisterGroupDetailInfoDto.getEstiType()) &&
                    contractInfoJsonDataDto.getOrderNum().contentEquals(contractRegisterGroupDetailInfoDto.getOrderNum()) &&
                    sdf.format(innerContractInfoJsonDtos.get(i).getLisStartDate()).contentEquals(contractRegisterGroupDetailInfoDto.getStartDate()) &&
                    sdf.format(innerContractInfoJsonDtos.get(i).getLisEndDate()).contentEquals(contractRegisterGroupDetailInfoDto.getEndDate()) &&
                    contractInfoJsonDataDto.getLisProNm().contentEquals(contractRegisterGroupDetailInfoDto.getLisProNm()) &&
                    contractInfoJsonDataDto.getKmsId().contentEquals(contractRegisterGroupDetailInfoDto.getKmsId()) &&
                    contractInfoJsonDataDto.getHwSn().contentEquals(contractRegisterGroupDetailInfoDto.getHwSn()) &&
                    innerContractInfoJsonDtos.get(i).getCoreCount().equals(contractRegisterGroupDetailInfoDto.getCpuCount()) &&
                    contractInfoJsonDataDto.getLisVerUp().contentEquals(contractRegisterGroupDetailInfoDto.getLisVerUp()) &&
                    contractInfoJsonDataDto.getLisTypeOcUp().contentEquals(contractRegisterGroupDetailInfoDto.getLisTypeOcUp()) &&
                    contractInfoJsonDataDto.getCloudSp().contentEquals(contractRegisterGroupDetailInfoDto.getCloudSp()) &&
                    contractInfoJsonDataDto.getCloudCsi().contentEquals(contractRegisterGroupDetailInfoDto.getCloudCsi()) &&
                    contractInfoJsonDataDto.getCloudSgn().contentEquals(contractRegisterGroupDetailInfoDto.getCloudSgn()) &&
                    contractInfoJsonDataDto.getInstanceNm().contentEquals(contractRegisterGroupDetailInfoDto.getInstanceNm()) &&
                    contractInfoJsonDataDto.getSymApiYn().contentEquals(contractRegisterGroupDetailInfoDto.getSymApiYn()) &&
                    contractInfoJsonDataDto.getPubApiYn().contentEquals(contractRegisterGroupDetailInfoDto.getPubApiYn()) &&
                    contractInfoJsonDataDto.getKewinApiYn().contentEquals(contractRegisterGroupDetailInfoDto.getKewinApiYn()) &&
                    contractInfoJsonDataDto.getApiYn().contentEquals(contractRegisterGroupDetailInfoDto.getApiYn()) &&
                    contractInfoJsonDataDto.getKmsYn().contentEquals(contractRegisterGroupDetailInfoDto.getKmsYn()) &&
                    contractInfoJsonDataDto.getOsYn().contentEquals(contractRegisterGroupDetailInfoDto.getOsYn()) &&
                    contractInfoJsonDataDto.getLisOsType().contentEquals(contractRegisterGroupDetailInfoDto.getLisOsType()) &&
                    contractInfoJsonDataDto.getKmYn().contentEquals(contractRegisterGroupDetailInfoDto.getKmYn()) &&
                    contractInfoJsonDataDto.getFileYn().contentEquals(contractRegisterGroupDetailInfoDto.getFileYn()) &&
                    contractInfoJsonDataDto.getHashYn().contentEquals(contractRegisterGroupDetailInfoDto.getHashYn()) &&
                    contractInfoJsonDataDto.getKmsEnc().contentEquals(contractRegisterGroupDetailInfoDto.getKmsEnc()) &&
                    contractInfoJsonDataDto.getKmsPubKey().contentEquals(contractRegisterGroupDetailInfoDto.getKmsPubKey()) &&
                    contractInfoJsonDataDto.getKmsPriKey().contentEquals(contractRegisterGroupDetailInfoDto.getKmsPriKey())
            ) {
                findAllContractInfoDtos.add(contractInfoJsonDataDto);
            }
            i++;
        }

        if (findAllContractInfoDtos.size() == 0)
            throw new NotFoundDataException();

        return new ContractInfoJsonDto.FindAllContractInfoDtoResp(
                ContractInfoJsonDto.ResultDto
                        .builder()
                        .result(ResultType.SUCCESS.ordinal())
                        .msg(ResultType.SUCCESS.name()).build(),
                findAllContractInfoDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindContractCustNameGroupInfoResp FindGroupByCustNmAndLisProNm() throws JsonProcessingException {
        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoJsonRepository.findAll());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(innerContractInfoJsonDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        HashMap<String, ContractCustNameGroupInfoDto> hashMap = new HashMap<>();
        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            ContractCustNameGroupInfoDto contractCustNameGroupInfoDto
                    = new ContractCustNameGroupInfoDto(contractInfoJsonDataDto.getCustNm(), contractInfoJsonDataDto.getLisProNm());
            hashMap.put(contractInfoJsonDataDto.getCustNm() + "," + contractInfoJsonDataDto.getLisProNm(), contractCustNameGroupInfoDto);
        }

        List<ContractCustNameGroupInfoDto> contractCustNameGroupInfoDtos = new ArrayList<>();
        for (Map.Entry<String, ContractCustNameGroupInfoDto> entry : hashMap.entrySet()) {
            contractCustNameGroupInfoDtos.add(new ContractCustNameGroupInfoDto(entry.getValue().getCustName(), entry.getValue().getLisProNm()));
        }
/*
        List<Map> newList = contractInfoJsonDataDtos.stream()
                                                     .collect(Collectors.groupingBy((map) -> map.getCustNm() + "," + map.getLisProNm()))
                                                     .entrySet().stream()
                                                     .map(entry -> {
                                                                     Map map = new HashMap();
                                                                     map.put(entry.getKey(), entry.getValue());
                                                                     return map;
                                                                   })
                                                     .collect(Collectors.toList());
*/
        return new ContractInfoJsonDto.FindContractCustNameGroupInfoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                contractCustNameGroupInfoDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindContractCustNameGroupInfoResp FindGroupByLisProNmAndLisTypeOcUp(String custName) throws JsonProcessingException {
        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(
                contractInfoJsonRepository.findByCustNm(
                        PageRequest.of(0, 65535), custName).getContent());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        HashMap<String, Long> hashMap = new HashMap<String, Long>();
        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            String dupData = contractInfoJsonDataDto.getLisProNm() + "," + contractInfoJsonDataDto.getLisTypeOcUp();
            hashMap.put(dupData, hashMap.getOrDefault(dupData, 0L) + 1);
        }

        List<ContractCustNameGroupInfoDto> contractCustNameGroupInfoDtos = new ArrayList<>();
        for (String distinctKey : hashMap.keySet()) {
            String[] distinctValue = distinctKey.split(",");

            contractCustNameGroupInfoDtos.add(new ContractCustNameGroupInfoDto(distinctValue[0],
                    distinctValue[1],
                    hashMap.get(distinctKey)));
        }

        return new ContractInfoJsonDto.FindContractCustNameGroupInfoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                contractCustNameGroupInfoDtos);
    }

    @Transactional(readOnly = true)
    public ContractInfoJsonDto.FindContractCustNameDetailInfoResp findDetailInfoByCustNameAndLisProNm(Integer currentPageNo,
                                                                                                      Integer recordsPerPage,
                                                                                                      String custName,
                                                                                                      String lisProNm) throws JsonProcessingException, ParseException {
        Page<ContractInfoJsonEntity> contractInfoJsonEntities = contractInfoJsonRepository.findByCustNmAndLisProNm(
                PageRequest.of(currentPageNo - 1, recordsPerPage),
                custName,
                lisProNm);

        List<ContractInfoJsonDto.InnerContractInfoJsonDto> innerContractInfoJsonDtos
                = ContractInfoJsonMapper.INSTANCE.findAllToDtos(contractInfoJsonEntities.getContent());

        List<ContractInfoJsonDto.ContractInfoJsonDataDto> contractInfoJsonDataDtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (ContractInfoJsonDto.InnerContractInfoJsonDto contractInfoJsonDataDto : innerContractInfoJsonDtos) {
            contractInfoJsonDataDtos.add
                    (objectMapper.readValue(contractInfoJsonDataDto.getContractInfoJson(),
                            ContractInfoJsonDto.ContractInfoJsonDataDto.class));
        }

        contractInfoJsonDataDtos.forEach(t -> {
            t.setEstiType(null);
        });

        List<ContractInfoJsonDto.ContractCustNameDetailInfoDto> contractCustNameDetailInfoDtos = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto : contractInfoJsonDataDtos) {
            contractCustNameDetailInfoDtos.add(ContractInfoJsonDto.ContractCustNameDetailInfoDto
                    .builder()
                    .lisSDate(sdf.parse(contractInfoJsonDataDto.getLisSDate()))
                    .lisEDate(sdf.parse(contractInfoJsonDataDto.getLisEDate()))
                    .insSDt(sdf.parse(contractInfoJsonDataDto.getInsSDt()))
                    .insEDt(sdf.parse(contractInfoJsonDataDto.getInsEDt()))
                    .custNm(contractInfoJsonDataDto.getCustNm())
                    .lisTypeOcUp(contractInfoJsonDataDto.getLisTypeOcUp())
                    .estiType(contractInfoJsonDataDto.getEstiType())
                    .loCore(contractInfoJsonDataDto.getLoCore())
                    .orderNum(contractInfoJsonDataDto.getOrderNum())
                    .apiYn(contractInfoJsonDataDto.getApiYn())
                    .fileYn(contractInfoJsonDataDto.getFileYn())
                    .hashYn(contractInfoJsonDataDto.getHashYn())
                    .kewinApiYn(contractInfoJsonDataDto.getKewinApiYn())
                    .kmsEnc(contractInfoJsonDataDto.getKmsEnc())
                    .kmsPriKey(contractInfoJsonDataDto.getKmsPriKey())
                    .kmsPubKey(contractInfoJsonDataDto.getKmsPubKey())
                    .kmsYn(contractInfoJsonDataDto.getKmsYn())
                    .kmYn(contractInfoJsonDataDto.getKmYn())
                    .osYn(contractInfoJsonDataDto.getOsYn())
                    .pubApiYn(contractInfoJsonDataDto.getPubApiYn())
                    .symApiYn(contractInfoJsonDataDto.getSymApiYn())
                    .build());
        }

        return new ContractInfoJsonDto.FindContractCustNameDetailInfoResp(
                ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build(),
                ContractInfoJsonDto.PageDto.builder()
                        .pageTotalSize((long) contractInfoJsonEntities.getTotalPages())
                        .build(),
                contractCustNameDetailInfoDtos);
    }

    /*
    // 미사용
    @Transactional(readOnly = true)
    public ContractTicketCountInfoDto.ContractTicketCountInfoDtoResp findContractTicketCountInfo() {
        List<ContractTicketCountInfoDto> contractTicketCountInfoDtos
                = contractInfoJsonRepository.findContractTicketCountInfo(LicenseType.COMMERCIAL.name(),
                LicenseType.CLOUD_INSTANCE.name());
        int idx = 0;
        for (ContractTicketCountInfoDto contractTicketCountInfoDto : contractTicketCountInfoDtos) {
            contractTicketCountInfoDto.setLicenseType(LicenseType.valueOf(contractTicketCountInfoDto.getLicenseType()).name());
            contractTicketCountInfoDtos.set(idx, contractTicketCountInfoDto);
            idx++;
        }

        return new ContractTicketCountInfoDto.ContractTicketCountInfoDtoResp(
                ContractInfoDto.ResultDto
                        .builder()
                        .result(ResultType.SUCCESS.ordinal())
                        .msg(ResultType.SUCCESS.name()).build(),
                contractTicketCountInfoDtos);
    }
    */

    @Transactional
    public ContractInfoJsonDto.ResultDto saveContractInfo(List<ContractInfoJsonDto.SaveContractInfoDtoReq> saveContractInfoDtoReqs) {
        ContractInfoJsonDto.InnerContractInfoJsonDto.InnerContractInfoJsonDtoBuilder innerContractInfoJsonDtoBuilder
                = new ContractInfoJsonDto.InnerContractInfoJsonDto().builder();

        saveContractInfoDtoReqs.forEach(t -> {
            try {
                requestMessageToEntity(innerContractInfoJsonDtoBuilder, t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        logContractInfo(saveContractInfoDtoReqs);

        return ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build();
    }

    @Transactional
    public ContractInfoJsonDto.ResultDto updateContractInfo(List<ContractInfoJsonDto.SaveContractInfoDtoReq> updateContractInfoDtoReqs) {
        ContractInfoJsonDto.InnerContractInfoJsonDto.InnerContractInfoJsonDtoBuilder innerContractInfoJsonDtoBuilder
                = new ContractInfoJsonDto.InnerContractInfoJsonDto().builder();

        updateContractInfoDtoReqs.forEach(t -> {
            ContractInfoJsonEntity selContractInfoEntity
                    = contractInfoJsonRepository.findByKeyIdx(t.getKeyIdx());

            if (selContractInfoEntity == null || selContractInfoEntity.equals(null)) {
                throw new NotFoundDataException("Not Found Data Error");
            }
            try {
                requestMessageToEntity(innerContractInfoJsonDtoBuilder, t);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build();
    }

    private void requestMessageToEntity(
            ContractInfoJsonDto.InnerContractInfoJsonDto.InnerContractInfoJsonDtoBuilder innerContractInfoJsonDtoBuilder,
            ContractInfoJsonDto.SaveContractInfoDtoReq t) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if (t.getEstiType().contentEquals("10")) {
            innerContractInfoJsonDtoBuilder
                    .lisStartDate(format.parse(t.getLisSDate()))
                    .lisEndDate(format.parse(t.getLisEDate()))
                    .build();
        } else {
            innerContractInfoJsonDtoBuilder
                    .lisStartDate(format.parse(t.getInsSDt()))
                    .lisEndDate(format.parse(t.getInsEDt()))
                    .build();
        }


        // 컴퍼넌트 항목
        if (t.getLisProNm().contentEquals(ComponentType.BA_SCP.getOrgName())) {

        } else if (t.getLisProNm().contentEquals(ComponentType.DAmo_KE.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.SUBSCRIPTION.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.DE_MYQ.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.DP_MSQ.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.DP_ORA.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.BA_UND.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_SCALE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.BA_P11.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_SCALE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.BA_POS.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_CORE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_SCALE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.KE_LNX.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.DA.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.SG_KMS.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.SG_KMS_SA.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.DE_PGS.getOrgName())) {
            if (!t.getLisTypeOcUp().contentEquals(LicenseType.COMMERCIAL.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.DEV.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.TEST.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.VOLUME.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_INSTANCE.name()) &&
                    !t.getLisTypeOcUp().contentEquals(LicenseType.CLOUD_METERING.name())
            ) {
                throw new InValidLicenseTypeException();
            }
        } else if (t.getLisProNm().contentEquals(ComponentType.WAPPLES.getOrgName())) {

        } else if (t.getLisProNm().contentEquals(ComponentType.ISIGN.getOrgName())) {

        } else {
            throw new NotFoundComponentTypeException();
        }

        innerContractInfoJsonDtoBuilder
                .lisProNm(t.getLisProNm())
                .build();
        t.setLisProNm(t.getLisProNm());

        if (t.getLisProNm() == null)
            throw new ContractInputValueCheckException();

        innerContractInfoJsonDtoBuilder
                .lisTypeOcUp(LicenseType.getName(t.getLisTypeOcUp()))
                .build();
        t.setLisTypeOcUp(LicenseType.getName(t.getLisTypeOcUp()));

        if (t.getLisTypeOcUp() == null)
            throw new ContractInputValueCheckException();

        if (t.getLisVerUp() == null ||
                t.getLisVerUp().isEmpty() ||
                t.getLisVerUp().contentEquals("3")) {
            t.setLisVerUp("3.0");
        } else if (t.getLisVerUp().contentEquals("4")) {
            t.setLisVerUp("4.0");
        } else if (t.getLisVerUp().contains(".")) {
        } else {
            throw new LicenseVersionIsMissMatchException();
        }

        if (t.getLisProNm().contentEquals(ComponentType.SG_KMS_SA.getOrgName())) {
            innerContractInfoJsonDtoBuilder
                    .coreCount(t.getVcoreCnt())
                    .build();
        } else {
            innerContractInfoJsonDtoBuilder
                    .coreCount(t.getLoCore())
                    .build();
        }

        if (t.getWebCnt() == null) {
            t.setWebCnt(-1);
        }

        if (t.getLisExpAct() == null) {
            t.setLisExpAct(0);
        }

        ContractInfoJsonEntity selContractInfoEntity
                = contractInfoJsonRepository.findByKeyIdx(t.getKeyIdx());

        if (selContractInfoEntity == null) {
            t.setLicenseStatus("0");
            innerContractInfoJsonDtoBuilder
                    .licenseStatus("0")
                    .build();
        } else {
            LicenseInfoDto licenseInfoDto
                    = LicenseMapper.INSTANCE.entityToLicenseInfoDto(
                    licenseInfoJpaRepo
                            .findByKeyIdxAndStatusIn(
                                    t.getKeyIdx()
                                    , new ArrayList<TypeDefine.Status>(
                                            Arrays.asList(
                                                    TypeDefine.Status.ISSUE_LICENSE
                                                    , TypeDefine.Status.LICENSE_IN_USE))));

            if (licenseInfoDto != null) {
                throw new AlreadyUsedContractInfoException();
            }

            t.setLicenseStatus(selContractInfoEntity.getLicenseStatus());
            innerContractInfoJsonDtoBuilder
                    .licenseStatus(selContractInfoEntity.getLicenseStatus())
                    .build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ContractInfoJsonEntity contractInfoJsonEntity = contractInfoJsonRepository.save(
                    ContractInfoJsonMapper.INSTANCE.dtoToEntity(
                            innerContractInfoJsonDtoBuilder.keyIdx(t.getKeyIdx())
                                    .contractFileName(t.getContractFileName())
                                    .custNm(t.getCustNm())
                                    .orderNum(t.getOrderNum())
                                    .lisTypeOcUp(t.getLisTypeOcUp())
                                    .contractInfoJson(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(t))
                                    .build()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public ContractInfoJsonDto.ResultDto updateLicenseStatus(ContractInfoJsonDto.UpdateLicenseStatusReq updateLicenseStatusReq) throws JsonProcessingException {
        ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto
                = ContractInfoJsonMapper.INSTANCE.EntityToDto(
                contractInfoJsonRepository.findByContractFileNameAndKeyIdx(
                        updateLicenseStatusReq.getContractFileName(),
                        updateLicenseStatusReq.getKeyIdx()));

        if (innerContractInfoJsonDto == null || innerContractInfoJsonDto.equals(null)) {
            throw new NotFoundDataException("Not Found Data Error");
        }

        // license status 0:unused 1:used
        if (!innerContractInfoJsonDto.getLicenseStatus().contentEquals(updateLicenseStatusReq.getBeforeLicenseStatus())) {
            throw new LicenseStatusMissMatchException();
        }

        innerContractInfoJsonDto.setLicenseStatus(updateLicenseStatusReq.getAfterLicenseStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto
                = objectMapper.readValue(
                innerContractInfoJsonDto.getContractInfoJson(),
                ContractInfoJsonDto.ContractInfoJsonDataDto.class);

        contractInfoJsonDataDto.setLicenseStatus(updateLicenseStatusReq.getAfterLicenseStatus());

        innerContractInfoJsonDto.setContractInfoJson(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractInfoJsonDataDto));

        ContractInfoJsonEntity contractInfoJsonEntity
                = contractInfoJsonRepository.save(
                ContractInfoJsonMapper.INSTANCE.dtoToEntity(innerContractInfoJsonDto));

        return ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build();
    }

    @Transactional
    public ContractInfoJsonDto.ResultDto deleteContractInfo(List<ContractInfoJsonDto.ContractInfoJsonDataDto> deleteContractInfoDtoReqs) {
        for (ContractInfoJsonDto.ContractInfoJsonDataDto deleteContractInfoDtoReq : deleteContractInfoDtoReqs) {
            ContractInfoJsonEntity selContractInfoEntity
                    = contractInfoJsonRepository.findByKeyIdx(deleteContractInfoDtoReq.getKeyIdx());

            if (selContractInfoEntity == null) {
                throw new NotFoundDataException("Not Found Data Error Key_Idx:" + deleteContractInfoDtoReq.getKeyIdx());
            }

            contractInfoJsonRepository.delete(selContractInfoEntity);

            logModuleService.SaveEventLog(LogLevelType.INFO,
                    "[Contract] " + deleteContractInfoDtoReq.getCustNm() + " " + deleteContractInfoDtoReq.getLisProNm() +
                            "(" + deleteContractInfoDtoReq.getOrderNum() + ")" +
                            " contract information deleted.");
        }

        return ContractInfoJsonDto.ResultDto.builder().result(ResultType.SUCCESS.ordinal()).msg(ResultType.SUCCESS.name()).build();
    }

    public void logContractInfo(List<ContractInfoJsonDto.SaveContractInfoDtoReq> contractInfoDtoReqs){

        HashMap<Integer, String> requestData = new HashMap<>();

        for (int i = 0; i < contractInfoDtoReqs.size(); i++) {
            String reqValue = contractInfoDtoReqs.get(i).getKmsPriKey() + "," +
                    contractInfoDtoReqs.get(i).getEndUserId() + "," +
                    contractInfoDtoReqs.get(i).getEstiType() + "," +
                    contractInfoDtoReqs.get(i).getOrderNum() + "," +
                    contractInfoDtoReqs.get(i).getLisSDate() + "," +
                    contractInfoDtoReqs.get(i).getLisEDate() + "," +
                    contractInfoDtoReqs.get(i).getInsEDt() + "," +
                    contractInfoDtoReqs.get(i).getInsEDt() + "," +
                    contractInfoDtoReqs.get(i).getLisProNm() + "," +
                    contractInfoDtoReqs.get(i).getKmsId() + "," +
                    contractInfoDtoReqs.get(i).getHwSn() + "," +
                    contractInfoDtoReqs.get(i).getVcoreCnt() + "," +
                    contractInfoDtoReqs.get(i).getLoCore() + "," +
                    contractInfoDtoReqs.get(i).getLisVerUp() + "," +
                    contractInfoDtoReqs.get(i).getLisTypeOcUp() + "," +
                    contractInfoDtoReqs.get(i).getCloudSp() + "," +
                    contractInfoDtoReqs.get(i).getCloudCsi() + "," +
                    contractInfoDtoReqs.get(i).getCloudSgn() + "," +
                    contractInfoDtoReqs.get(i).getInstanceNm() + "," +
                    contractInfoDtoReqs.get(i).getSymApiYn() + "," +
                    contractInfoDtoReqs.get(i).getPubApiYn() + "," +
                    contractInfoDtoReqs.get(i).getKewinApiYn() + "," +
                    contractInfoDtoReqs.get(i).getApiYn() + "," +
                    contractInfoDtoReqs.get(i).getKmsYn() + "," +
                    contractInfoDtoReqs.get(i).getOsYn() + "," +
                    contractInfoDtoReqs.get(i).getSiteNm() + "," +
                    contractInfoDtoReqs.get(i).getLisOsType() + "," +
                    contractInfoDtoReqs.get(i).getKmYn() + "," +
                    contractInfoDtoReqs.get(i).getFileYn() + "," +
                    contractInfoDtoReqs.get(i).getHashYn() + "," +
                    contractInfoDtoReqs.get(i).getKmsEnc() + "," +
                    contractInfoDtoReqs.get(i).getKmsPubKey() + "," +
                    contractInfoDtoReqs.get(i).getWebCnt() + "," +
                    contractInfoDtoReqs.get(i).getLisExpAct() + "," +
                    contractInfoDtoReqs.get(i).getCustNm();

            // hashmap 에 keyidx, request value를 put 함. request 개수 만큼 put
            // hashmap = keyidx, value
            requestData.put(contractInfoDtoReqs.get(i).getKeyIdx(), reqValue);
        }

        Map<String, Integer> distinctData = new HashMap<>();

        for (Integer requestDataKeyIdx : requestData.keySet()) {
            String requestDataValue = requestData.get(requestDataKeyIdx);

            // value 기준의 데이터를 hashmap에 put, value가 중복된 데이터는 count+1하여 put함
            // hashmap = value, 중복 count
            distinctData.put(requestDataValue, distinctData.getOrDefault(requestDataValue, 0) + 1);
        }

        for (String distinctDataValue : distinctData.keySet()) {
            Integer distinctDataCount = distinctData.get(distinctDataValue);
            String[] keyContractInfo = distinctDataValue.split(",");

            logModuleService.SaveEventLog(LogLevelType.INFO,
                    "[Contract] " + keyContractInfo[0] + " : " + keyContractInfo[8] +
                            "(" + keyContractInfo[3] + ")" +
                            " contract information registered.(" + distinctDataCount + " COPY).");
        }
    }
}
