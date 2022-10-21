package com.penta.ps.license.license.service.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.ps.license.license.component.LicenseGenerator;
import com.penta.ps.license.license.dto.contract.ContractExtendedInfoDto;
import com.penta.ps.license.license.response.*;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.OperatorCreationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class ResponseService {
    // 단일 결과 리턴
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);

        return result;
    }

    // 단일 결과 리턴 with serial-no
    public <T> SingleResultWithSerialNo<T> getSingleResultWithSerialNo(T data) {
        SingleResultWithSerialNo<T> result = new SingleResultWithSerialNo<>();
        result.setSerialNo(data);
        setSuccessResult(result);

        return result;
    }

    // 단일 결과 리턴 with license-id(serial-no)
    public <T> SingleResultWithLicenseId<T> getSingleResultWithLicenseId(T data) {
        SingleResultWithLicenseId<T> result = new SingleResultWithLicenseId<>();
        result.setLicenseId(data);
        setSuccessResult(result);

        return result;
    }

    // 단일 결과 리턴 with status
    public <T> SingleResultWithStatus<T> getSingleResultWithStatus(T data) {
        SingleResultWithStatus<T> result = new SingleResultWithStatus<>();
        result.setStatus(data);
        setSuccessResult(result);

        return result;
    }

    public <T> SingleResultWithTotalAmount<T> getSingleResultWithTotalAmount(T data) {
        SingleResultWithTotalAmount<T> result = new SingleResultWithTotalAmount<>();
        result.setTotalAmount(data);
        setSuccessResult(result);

        return result;
    }

    public <T> SingleResultWithAmountDetail<T> getSingleResultWithAmountDetail(T data) {
        SingleResultWithAmountDetail<T> result = new SingleResultWithAmountDetail<>();
        result.setAmountList(data);
        setSuccessResult(result);

        return result;
    }

    public <T> SingleResultWithAmountHalfYear<T> getSingleResultWithAmountHalfYear(T data) {
        SingleResultWithAmountHalfYear<T> result = new SingleResultWithAmountHalfYear<>();
        result.setHalfYearAmountList(data);
        setSuccessResult(result);

        return result;
    }

    // 단일 결과 리턴 with Contract Data
    public <T> SingleResultWithContractExtendedField<T> getSingleResultWithContractExtendedField(T startData, T endData, ContractExtendedInfoDto contractExtendedInfoDto) {
        SingleResultWithContractExtendedField<T> result = new SingleResultWithContractExtendedField<>();
        result.setStartDate(startData);
        result.setEndDate(endData);
        result.setExtendedField(contractExtendedInfoDto);
        setSuccessResult(result);

        return result;
    }

    public <T> SingleResultWithCertificate getSingleResultWithCertificate(Date startDate,
                                                                          Date endDate,
                                                                          T contractExtendedInfoDto)
            throws IOException, ParseException, GeneralSecurityException, OperatorCreationException {
        SingleResultWithCertificate result = new SingleResultWithCertificate();
        StringWriter stringWriter = new StringWriter();
        JcaPEMWriter pemWriter = new JcaPEMWriter(stringWriter);
        ObjectMapper mapper = new ObjectMapper();
        LicenseGenerator licenseGenerator = new LicenseGenerator();

        X509Certificate cert = licenseGenerator.generatePentaCAIssuedCertificate(
                "CN=PENTA, OU=ISSAC Authentication Service, O=Penta Security Systems Inc., C=KR",
                startDate,
                endDate,
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractExtendedInfoDto)
        );

        pemWriter.writeObject(cert);
        pemWriter.flush();
        pemWriter.close();

        result.setCertificate(stringWriter.toString());
        setSuccessResult(result);

        return result;
    }

    // 복수 결과 리턴 with status, license type
    public <T> MultipleResultWithStatusNLicenseType<T> getMultipleResultWithStatusNLicenseType(T status, T licenseType) {
        MultipleResultWithStatusNLicenseType<T> result = new MultipleResultWithStatusNLicenseType<>();
        result.setStatus(status);
        result.setLicenseType(licenseType);
        setSuccessResult(result);

        return result;
    }

    // 복수 결과 리턴 with 티켓 사용개수, cloud 사용개수
    public <T> MultipleResultWithServiceCountInUse<T> getMultipleResultWithServiceCountInUse(T map) {
        MultipleResultWithServiceCountInUse<T> result = new MultipleResultWithServiceCountInUse<>();
        result.setServiceCountInUse(map);
        setSuccessResult(result);

        return result;
    }

    // 복수 결과 리턴 with 티켓 사용개수(component 기준), cloud 사용 시간(component 기준)
    public <T> MultipleResultWithServiceInfoInUse<T> getMultipleResultWithServiceInfoInUse(T map) {
        MultipleResultWithServiceInfoInUse<T> result = new MultipleResultWithServiceInfoInUse<>();
        result.setServiceInfoInUse(map);
        setSuccessResult(result);

        return result;
    }

    // 다중건 결과 리턴
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setLicenseList(list);
        setSuccessResult(result);

        return result;
    }

    // 다중건 결과 리턴 with 전체 페이지
    public <T> ListResultWithPageTotalSize<T> getListResultWithPageTotalSize(List<T> list, int pageTotalSize) {
        ListResultWithPageTotalSize<T> result = new ListResultWithPageTotalSize<>();
        result.setLicenseList(list);

        result.setPageTotalSize(pageTotalSize);
        result.setResult(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());

        return result;
    }

    // 성공 결과만 리턴
    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 결과에 성공 데이터 세팅
    private void setSuccessResult(CommonResult result) {
        result.setResult(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    // 실패 결과만 리턴
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        //result.setSuccess(false);
        result.setResult(code);
        result.setMsg(msg);
        return result;
    }

    // 실패 결과 또는 일반 결과(어떤값이 들어올지 모르는) 리턴
    public CommonResult getFailOrCommonResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setResult(code);
        result.setMsg(msg);
        return result;
    }

    // enum으로 요청 결과에 대한 code, msg를 정의한다.
    public enum CommonResponse {
        SUCCESS(0, "success"),
        FAIL(1, "fail");

        int result;
        String msg;

        CommonResponse(int result, String msg) {
            this.result = result;
            this.msg = msg;
        }

        public int getCode() {
            return result;
        }

        public String getMsg() {
            return msg;
        }
    }

}
