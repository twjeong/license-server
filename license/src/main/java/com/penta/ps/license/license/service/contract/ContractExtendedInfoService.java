package com.penta.ps.license.license.service.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.ps.license.license.dto.contract.ContractExtendedInfoDto;
import com.penta.ps.license.license.dto.contract.ContractInfoJsonDto;
import com.penta.ps.license.license.exception.contract.ComponentVersionIsNullException;
import com.penta.ps.license.license.exception.contract.NotFoundComponentTypeException;
import com.penta.ps.license.license.type.ComponentType;
import com.penta.ps.license.license.type.LicenseType;
import com.penta.ps.license.license.type.contract.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractExtendedInfoService {
    public void setContractExtendInfo(String componentVersion,
                                      ContractInfoJsonDto.InnerContractInfoJsonDto innerContractInfoJsonDto,
                                      ContractExtendedInfoDto contractExtendedInfoDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto
                = (objectMapper.readValue(
                innerContractInfoJsonDto.getContractInfoJson(),
                ContractInfoJsonDto.ContractInfoJsonDataDto.class));

        // 기본항목(공통)
        contractExtendedInfoDto.setStartDate(innerContractInfoJsonDto.getLisStartDate());
        contractExtendedInfoDto.setEndDate(innerContractInfoJsonDto.getLisEndDate());
        contractExtendedInfoDto.setCpuCount(innerContractInfoJsonDto.getCoreCount());

        // 기본항목(공통)
        setContractCommonExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);

        // 컴퍼넌트 항목
        if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.BA_SCP.getOrgName())) {
            setContractBaScpExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DAmo_KE.getOrgName())) {
            setContractKeWinExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DE_MYQ.getOrgName())) {
            setContractDeMyqExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DP_MSQ.getOrgName())) {
            setContractDpMsqExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DP_ORA.getOrgName())) {
            setContractDpOraExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.BA_UND.getOrgName())) {
            setContractBaUndExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.BA_P11.getOrgName())) {
            setContractBaP11ExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.BA_POS.getOrgName())) {
            setContractBaPosExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.KE_LNX.getOrgName())) {
            setContractKeLnxExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DA.getOrgName())) {
            setContractDaExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.SG_KMS.getOrgName())) {
            setContractSgKmsExtendInfo(componentVersion, contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.SG_KMS_SA.getOrgName())) {
            setContractSgKmsSaExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.DE_PGS.getOrgName())) {
            setContractDePgsExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.WAPPLES.getOrgName())) {
            setContractWapplesExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else if (innerContractInfoJsonDto.getLisProNm().contentEquals(ComponentType.ISIGN.getOrgName())) {
            setContractIsignExtendInfo(contractInfoJsonDataDto, contractExtendedInfoDto);
        } else {
            throw new NotFoundComponentTypeException();
        }
    }

    public void setContractCommonExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                            ContractExtendedInfoDto contractExtendedInfoDto) {
        // 기본항목(공통)
        contractExtendedInfoDto.setVersion(contractInfoJsonDataDto.getLisVerUp());
        contractExtendedInfoDto.setType(LicenseType.valueOf(contractInfoJsonDataDto.getLisTypeOcUp()).getName());
        contractExtendedInfoDto.setProductName(ComponentType.getNewProductName(contractInfoJsonDataDto.getLisProNm()));

        // 유지보수항목
        contractExtendedInfoDto.setContractNumber(contractInfoJsonDataDto.getOrderNum());

        // 클라우드 인스턴스, 클라우드 코어
        if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_INSTANCE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_CORE.getName())) {
            contractExtendedInfoDto.setCloudServiceProvider(contractInfoJsonDataDto.getCloudSp());
            contractExtendedInfoDto.setCloudServiceID(contractInfoJsonDataDto.getCloudCsi());
        }
        // 클라우드 스케일
        else if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_SCALE.getName())) {
            contractExtendedInfoDto.setCloudServiceProvider(contractInfoJsonDataDto.getCloudSp());
            contractExtendedInfoDto.setCloudScaleGroupName(contractInfoJsonDataDto.getCloudSgn());
        }
        // 클라우드 미터링(종량제)
        else if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_METERING.getName())) {
            contractExtendedInfoDto.setCloudServiceProvider(contractInfoJsonDataDto.getCloudSp());
            contractExtendedInfoDto.setCloudServiceID(contractInfoJsonDataDto.getCloudCsi());
            contractExtendedInfoDto.setCloudScaleGroupName(contractInfoJsonDataDto.getCloudSgn());
        }
    }

    public void setContractBaScpExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        // 번들외 라이선스
        if (!contractExtendedInfoDto.getType().contentEquals(LicenseType.BUNDLE.getName())) {
            contractExtendedInfoDto.setOptionSymmetricKeyEncryptionAPI(ThreeCaseFlagType.getName(contractInfoJsonDataDto.getSymApiYn()));
            contractExtendedInfoDto.setOptionEncryptionAPI(BooleanType.getName(contractInfoJsonDataDto.getApiYn()));
            contractExtendedInfoDto.setOptionExportKey(BooleanType.getName(contractInfoJsonDataDto.getKmsYn()));
            contractExtendedInfoDto.setOptionEncryptionPrivilege(BooleanType.getName(contractInfoJsonDataDto.getOsYn()));
            contractExtendedInfoDto.setOptionKeWinAPI(BooleanType.getName(contractInfoJsonDataDto.getKewinApiYn()));
            contractExtendedInfoDto.setOptionPublicKeyEncryptionAPI(TwoCaseFlagType.getName(contractInfoJsonDataDto.getPubApiYn()));
        } else { // 번들라이선스
            contractExtendedInfoDto.setOptionBundleOs(OsType.getName(contractInfoJsonDataDto.getLisOsType()));
            contractExtendedInfoDto.setOptionBundleKms(BooleanType.getName(contractInfoJsonDataDto.getKmYn()));
            contractExtendedInfoDto.setOptionBundleFile(BooleanType.getName(contractInfoJsonDataDto.getFileYn()));
            contractExtendedInfoDto.setOptionBundleHash(BooleanType.getName(contractInfoJsonDataDto.getHashYn()));
        }
    }

    public void setContractKeWinExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractDeMyqExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        // 컴퍼넌트 항목(하드코딩, 기획 김교남팀장 확인완료)
        contractExtendedInfoDto.setSubProductName("DE-MYQ");
        contractExtendedInfoDto.setDbCountLimit(2000);
    }

    public void setContractDpMsqExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractDpOraExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        // 컴퍼넌트 항목
        contractExtendedInfoDto.setInstanceName(contractInfoJsonDataDto.getInstanceNm());
    }

    public void setContractBaUndExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CORE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_CORE.getName())) {
        } else {
            // 기본항목(공통)
            contractExtendedInfoDto.setCpuCount(999);
        }
    }

    public void setContractBaP11ExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CORE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_CORE.getName())) {
        } else {
            // 기본항목(공통)
            contractExtendedInfoDto.setCpuCount(999);
        }
    }

    public void setContractBaPosExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractKeLnxExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractDaExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                        ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractSgKmsExtendInfo(String componentVersion,
                                           ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
        if (componentVersion.isEmpty() || componentVersion == null)
            throw new ComponentVersionIsNullException();

        // 기본항목(공통)
        contractExtendedInfoDto.setValidIp(null);

        if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_CORE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_INSTANCE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_SCALE.getName())) {
        } else {
        }
/*
        String[] array = componentVersion.split("\\.", 4);

        // ~ 4.0.104.0 hardwareId에 set
        // 4.0.104.1 ~ sgkmsId에 set
        if (Integer.parseInt(array[0]) >= 4 &&
                Integer.parseInt(array[1]) >= 0) {
            if (Integer.parseInt(array[2]) >= 104) {
                if (Integer.parseInt(array[3]) >= 1)
                    contractExtendedInfoDto.setSgkmsId(contractInfoJsonDataDto.getHwSn());
                else
                    contractExtendedInfoDto.setHardwareId(contractInfoJsonDataDto.getHwSn());
            } else
                contractExtendedInfoDto.setHardwareId(contractInfoJsonDataDto.getHwSn());
        } else {
            contractExtendedInfoDto.setHardwareId(contractInfoJsonDataDto.getHwSn());
        }
*/
        // 컴퍼넌트 항목
        contractExtendedInfoDto.setHardwareId(contractInfoJsonDataDto.getHwSn());
        contractExtendedInfoDto.setFunctionApplyService(BooleanType.getName(contractInfoJsonDataDto.getKmsEnc()));
        contractExtendedInfoDto.setFunctionApplyExternalKey(BooleanType.getName(contractInfoJsonDataDto.getKmsPubKey()));
        contractExtendedInfoDto.setFunctionApplyAsymKey(BooleanType.getName(contractInfoJsonDataDto.getKmsPriKey()));
    }

    public void setContractSgKmsSaExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                             ContractExtendedInfoDto contractExtendedInfoDto) {
        if (contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_CORE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_INSTANCE.getName()) ||
                contractExtendedInfoDto.getType().contentEquals(LicenseType.CLOUD_SCALE.getName())) {
        } else {
        }

        // 컴퍼넌트 항목
        contractExtendedInfoDto.setHardwareId(contractInfoJsonDataDto.getKmsId());
        contractExtendedInfoDto.setFunctionApplyService(BooleanType.getName(contractInfoJsonDataDto.getKmsEnc()));
        contractExtendedInfoDto.setFunctionApplyExternalKey(BooleanType.getName(contractInfoJsonDataDto.getKmsPubKey()));
        contractExtendedInfoDto.setFunctionApplyAsymKey(BooleanType.getName(contractInfoJsonDataDto.getKmsPriKey()));
    }

    public void setContractDePgsExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }

    public void setContractWapplesExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                             ContractExtendedInfoDto contractExtendedInfoDto) {
        contractExtendedInfoDto.setCpuCount(null);
        contractExtendedInfoDto.setVersion(null);
        contractExtendedInfoDto.setValidIp(null);
        contractExtendedInfoDto.setWebCnt(contractInfoJsonDataDto.getWebCnt());
        contractExtendedInfoDto.setLisExpAct(contractInfoJsonDataDto.getLisExpAct());
    }

    public void setContractIsignExtendInfo(ContractInfoJsonDto.ContractInfoJsonDataDto contractInfoJsonDataDto,
                                           ContractExtendedInfoDto contractExtendedInfoDto) {
    }
}
