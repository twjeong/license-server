package com.penta.ps.license.license.controller.license;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.penta.ps.license.license.exception.common.EtcException;
import com.penta.ps.license.license.exception.common.InputValueCheckException;
import com.penta.ps.license.license.exception.common.NotFoundDataException;
import com.penta.ps.license.license.exception.common.PrimarykeyDuplicationException;
import com.penta.ps.license.license.exception.contract.*;
import com.penta.ps.license.license.exception.license.LoadPublicKeyException;
import com.penta.ps.license.license.exception.license.VerifyJWTException;
import com.penta.ps.license.license.exception.license.*;
import com.penta.ps.license.license.response.CommonResult;
import com.penta.ps.license.license.service.common.LogModuleService;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.type.LogLevelType;
import io.netty.channel.ConnectTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.net.ssl.SSLException;
import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.nio.file.AccessDeniedException;
import java.sql.BatchUpdateException;
import java.sql.SQLSyntaxErrorException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;
    private final LogModuleService logModuleService;

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    protected CommonResult notReadableException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR
                , getMessage("httpMessageNotReadableException.result")
                        + ":" + getMessage("httpMessageNotReadableException.msg"));

        return responseService.getFailOrCommonResult(
                Integer.valueOf(getMessage("httpMessageNotReadableException.result"))
                , getMessage("httpMessageNotReadableException.msg"));
    }

    // customizing error 메시지
    @ExceptionHandler(SearchDataException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult searchDataException(HttpServletRequest request, SearchDataException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR
                , getMessage("searchDataException.result") + ":" + getMessage("searchDataException.msg"));
        return responseService.getFailOrCommonResult(
                Integer.valueOf(getMessage("searchDataException.result"))
                , getMessage("searchDataException.msg"));
    }

    @ExceptionHandler(GetToManagerException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult getToManagerException(HttpServletRequest request, GetToManagerException e) {
        logModuleService.SaveEventLog(LogLevelType.ERROR, "[" +
                Integer.valueOf(getMessage("getToManagerException.result")) + "] + "
                + getMessage("getToManagerException.msg"));

        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("getToManagerException.result"))
                , getMessage("getToManagerException.msg"));
    }

    @ExceptionHandler(SearchSerialNoException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult searchSerialNoException(HttpServletRequest request, SearchSerialNoException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("searchSerialNoException.result") + ":" + getMessage("searchSerialNoException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("searchSerialNoException.result")), getMessage("searchSerialNoException.msg"));
    }

    @ExceptionHandler(VerifyJWTException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult verifyJWTException(HttpServletRequest request, VerifyJWTException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("verifyJWTException.result") + ":" + getMessage("verifyJWTException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("verifyJWTException.result")), getMessage("verifyJWTException.msg"));
    }

    @ExceptionHandler(LoadPublicKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult loadPublicKeyException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("loadPublicKeyException.result") + ":" + getMessage("loadPublicKeyException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("loadPublicKeyException.result")), getMessage("loadPublicKeyException.msg"));
    }

    @ExceptionHandler(RequestBodyNullException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult requestBodyNullException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("requestBodyNullException.result") + ":" + getMessage("requestBodyNullException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("requestBodyNullException.result")), getMessage("requestBodyNullException.msg"));
    }

    @ExceptionHandler(ResponseBodyNullException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult responseBodyNullException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("responseBodyNullException.result") + ":" + getMessage("responseBodyNullException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("responseBodyNullException.result")), getMessage("responseBodyNullException.msg"));
    }

    @ExceptionHandler(AlreadyExistsDataException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult alreadyExistsDataException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("alreadyExistsDataException.result") + ":" + getMessage("alreadyExistsDataException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("alreadyExistsDataException.result")), getMessage("alreadyExistsDataException.msg"));
    }

    @ExceptionHandler(ContractInfoMatchFailException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult contractInfoMatchFailException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("contractInfoMatchFailException.result") + ":" + getMessage("contractInfoMatchFailException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("contractInfoMatchFailException.result")), getMessage("contractInfoMatchFailException.msg"));
    }

    @ExceptionHandler(LicenseStatusIsNotUsedException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult licenseStatusIsNotUsedException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("licenseStatusIsNotUsedException.result") + ":" + getMessage("licenseStatusIsNotUsedException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("licenseStatusIsNotUsedException.result")), getMessage("licenseStatusIsNotUsedException.msg"));
    }

    @ExceptionHandler(LicenseTypeIsNotCloudMeteringException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult licenseTypeIsNotCloudMeteringException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("licenseTypeIsNotCloudMeteringException.result") + ":" + getMessage("licenseTypeIsNotCloudMeteringException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("licenseTypeIsNotCloudMeteringException.result")), getMessage("licenseTypeIsNotCloudMeteringException.msg"));
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult fileNotFoundException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("fileNotFoundException.result") + ":" + getMessage("fileNotFoundException.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("fileNotFoundException.result")), getMessage("fileNotFoundException.msg"));
    }

    @ExceptionHandler(PrimarykeyDuplicationException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult primarykeyDuplicationException(HttpServletRequest request, PrimarykeyDuplicationException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("primarykeyDuplicationException.result") + ":" + getMessage("primarykeyDuplicationException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("primarykeyDuplicationException.result")), getMessage("primarykeyDuplicationException.msg"));
    }

    @ExceptionHandler(NotFoundDataException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult notFoundDataException(HttpServletRequest request, NotFoundDataException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("notFoundDataException.result") + ":" + getMessage("notFoundDataException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("notFoundDataException.result")), getMessage("notFoundDataException.msg"));
    }

    @ExceptionHandler(InputValueCheckException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult inputValueCheckException(HttpServletRequest request, InputValueCheckException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("inputValueCheckException.result") + ":" + getMessage("inputValueCheckException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("inputValueCheckException.result")), getMessage("inputValueCheckException.msg"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult httpRequestMethodNotSupportedException(HttpServletRequest request, HttpRequestMethodNotSupportedException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("httpRequestMethodNotSupportedException.result") + ":" + getMessage("httpRequestMethodNotSupportedException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("httpRequestMethodNotSupportedException.result")),
                getMessage("httpRequestMethodNotSupportedException.msg"));
    }

    @ExceptionHandler(InvalidDefinitionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult invalidDefinitionException(HttpServletRequest request, InvalidDefinitionException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("invalidDefinitionException.result") + ":" + getMessage("invalidDefinitionException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("invalidDefinitionException.result")),
                getMessage("invalidDefinitionException.msg"));
    }

    @ExceptionHandler(InValidJwtTokenException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult inValidJwtTokenException(HttpServletRequest request, InValidJwtTokenException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("inValidJwtTokenException.result") + ":" + getMessage("inValidJwtTokenException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("inValidJwtTokenException.result")), getMessage("inValidJwtTokenException.msg"));
    }

    @ExceptionHandler(NotFoundComponentTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult notFoundComponentTypeException(HttpServletRequest request, NotFoundComponentTypeException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("notFoundComponentTypeException.result") + ":" + getMessage("notFoundComponentTypeException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("notFoundComponentTypeException.result")), getMessage("notFoundComponentTypeException.msg"));
    }

    @ExceptionHandler(ComponentVersionIsNullException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult componentVersionIsNullException(HttpServletRequest request, ComponentVersionIsNullException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("componentVersionIsNullException.result") + ":" + getMessage("componentVersionIsNullException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("componentVersionIsNullException.result")), getMessage("componentVersionIsNullException.msg"));
    }

    @ExceptionHandler(LicenseVersionIsMissMatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult licenseVersionIsMissMatchException(HttpServletRequest request, LicenseVersionIsMissMatchException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("licenseVersionIsMissMatchException.result") + ":" + getMessage("licenseVersionIsMissMatchException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("licenseVersionIsMissMatchException.result")), getMessage("licenseVersionIsMissMatchException.msg"));
    }

    @ExceptionHandler(AlreadyUsedContractInfoException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult alreadyUsedContractInfoException(HttpServletRequest request, AlreadyUsedContractInfoException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("alreadyUsedContractInfoException.result") + ":" + getMessage("alreadyUsedContractInfoException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("alreadyUsedContractInfoException.result")), getMessage("alreadyUsedContractInfoException.msg"));
    }

    @ExceptionHandler(LicenseStatusMissMatchException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult licenseStatusMissMatchException(HttpServletRequest request, LicenseStatusMissMatchException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("licenseStatusMissMatchException.result") + ":" + getMessage("licenseStatusMissMatchException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("licenseStatusMissMatchException.result")), getMessage("licenseStatusMissMatchException.msg"));
    }

    @ExceptionHandler(InValidLicenseTypeException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult inValidLicenseTypeException(HttpServletRequest request, InValidLicenseTypeException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("inValidLicenseTypeException.result") + ":" + getMessage("inValidLicenseTypeException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("inValidLicenseTypeException.result")), getMessage("inValidLicenseTypeException.msg"));
    }

    // user exception msg add
    // customizing error 메시지
    @ExceptionHandler(EtcException.class)
    @ResponseStatus(HttpStatus.OK)
    public CommonResult etcException(HttpServletRequest request, EtcException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("etcException.result") + ":" + getMessage("etcException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("etcException.result")), getMessage("etcException.msg"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult BadRequestException(HttpServletRequest request, final RuntimeException ex) {
        log.warn("ExceptionController", ex);

        return responseService.getFailResult(400, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("ExceptionController", ex);

        return responseService.getFailResult(401, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.error("ExceptionController", ex);

        return responseService.getFailResult(500, ex.getMessage());
    }

    // default error 메시지
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR, getMessage("unKnown.result") + ":" + getMessage("unKnown.msg"));
        return responseService.getFailOrCommonResult(Integer.valueOf(getMessage("unKnown.result")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(ConnectTimeoutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult connectTimeoutException(HttpServletRequest request, ConnectTimeoutException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("connectTimeoutException.result") + ":" + getMessage("connectTimeoutException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("connectTimeoutException.result")),
                getMessage("connectTimeoutException.msg"));
    }

    @ExceptionHandler(ConnectException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult connectException(HttpServletRequest request, ConnectException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("connectException.result") + ":" + getMessage("connectException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("connectException.result")),
                getMessage("connectException.msg"));
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult jpaSystemException(HttpServletRequest request, JpaSystemException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("jpaSystemException.result") + ":" + getMessage("jpaSystemException.msg"));
        return responseService.getFailResult(Integer.parseInt(getMessage("jpaSystemException.result")),
                getMessage("jpaSystemException.msg"));
    }

    @ExceptionHandler(BatchUpdateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult batchUpdateException(HttpServletRequest request, BatchUpdateException e) {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("batchUpdateException.result") + ":" + getMessage("batchUpdateException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("batchUpdateException.result")),
                getMessage("batchUpdateException.msg"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult constraintViolationException(HttpServletRequest request, ConstraintViolationException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("constraintViolationException.result") + ":" + getMessage("constraintViolationException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("constraintViolationException.result")),
                getMessage("constraintViolationException.msg"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult dataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("dataIntegrityViolationException.result") + ":" + getMessage("dataIntegrityViolationException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("dataIntegrityViolationException.result")),
                getMessage("dataIntegrityViolationException.msg"));
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult sqlSyntaxErrorException(HttpServletRequest request, SQLSyntaxErrorException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("sqlSyntaxErrorException.result") + ":" + getMessage("sqlSyntaxErrorException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("sqlSyntaxErrorException.result")),
                getMessage("sqlSyntaxErrorException.msg"));
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult numberFormatException(HttpServletRequest request, NumberFormatException e) throws SSLException {
        logModuleService.SaveSystemLog(LogLevelType.ERROR,
                getMessage("numberFormatException.result") + ":" + getMessage("numberFormatException.msg"));
        return responseService.getFailResult(Integer.valueOf(getMessage("numberFormatException.result")),
                getMessage("numberFormatException.msg"));
    }

    /*
        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        protected CommonResult runtimeException(HttpServletRequest request, RuntimeException e) {
            if (e.getCause() instanceof ConnectException) {
                return this.connectException(request, new ConnectException(e.getMessage()));
            } else {
                logModuleService.SaveSystemLog(LogLevelType.ERROR,
                        getMessage("runtimeException.result") + ":" + getMessage("runtimeException.msg"));

                return responseService.getFailResult(Integer.valueOf(getMessage("runtimeException.result")),
                        getMessage("runtimeException.msg"));
            }
        }
     */

    // result 에 해당하는 메시지 조회
    private String getMessage(String result) {
        return getMessage(result, null);
    }

    // result 정보, param으로 현재 locale에 맞는 메시지 조회 (현재는 사용하지 않지만 미래를 위해 추가함)
    private String getMessage(String result, Object[] args) {
        return messageSource.getMessage(result, args, LocaleContextHolder.getLocale());
    }
}
