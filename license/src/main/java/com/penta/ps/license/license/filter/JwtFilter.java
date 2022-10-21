package com.penta.ps.license.license.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.penta.ps.license.license.controller.common.GetPublicKey;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.common.VerifyJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
//public class JwtFilter extends GenericFilterBean {
public class JwtFilter extends OncePerRequestFilter {
    private final VerifyJwt verifyJwt;
    private final GetPublicKey getPublicKey;
    private final ResponseService responseService;
    private final MessageSource messageSource;

    @Override
    //public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // filter 내에서는 ExceptionAdvice 사용이 불가하기 때문에 user exception을 직접 만들어 response 해야 함

//        String CMToken = verifyJwt.resolveCMToken(request);
//        String pentaToken = verifyJwt.resolveToken(request);
//
//        if((CMToken == null && pentaToken == null) || (CMToken != null && pentaToken != null)){
//            occurVerifyJwtException(response);
//            return;
//        }
//
//        // filtering for CM
//        if(CMToken != null){
//            if(CMToken.compareTo("PENTA-CM-PS-TOKEN-NOTHING") == 0){
//                filterChain.doFilter(request, response);
//                return;
//            } else {
//                occurVerifyJwtException(response);
//                return;
//            }
//        }
//        // filtering for normal action
//        else if(pentaToken != null){
//
//            if(verifyJwt.validateToken(pentaToken, GetPublicKey.PUBLICKEY) == false) {
//
//                // 공개키가 없거나 바뀌었을 가능성이 있어 한번 더 로드 후 유효성 체크
//                try {
//                    getPublicKey.getPublicKeyFromUI();
//                } catch (NoSuchAlgorithmException e) {
//                    occurLoadPublicKeyException(response);
//                    return;
//                } catch (InvalidKeySpecException e) {
//                    occurLoadPublicKeyException(response);
//                    return;
//                }
//
//                if(verifyJwt.validateToken(pentaToken, GetPublicKey.PUBLICKEY) == false) {
//                    occurVerifyJwtException(response);
//                    return;
//                }
//            }
//        }

        //chain.doFilter(request, response);
        filterChain.doFilter(request, response);

        // jwt는 정상동작 하나 일단 alpha 버전에서는 skip
        /*
        String token = verifyJwt.resolveToken((HttpServletRequest) request);
        if(token == null){
            occurVerifyJwtException(response);
            return;
        }

        if(verifyJwt.validateToken(token, GetPublicKey.PUBLICKEY) == false) {

            // 공개키가 없거나 바뀌었을 가능성이 있어 한번 더 로드 후 유효성 체크
            try {
                getPublicKey.getPublicKeyFromUI();
            } catch (NoSuchAlgorithmException e) {
                occurLoadPublicKeyException(response);
                return;
            } catch (InvalidKeySpecException e) {
                occurLoadPublicKeyException(response);
                return;
            }

            if(verifyJwt.validateToken(token, GetPublicKey.PUBLICKEY) == false) {
                occurVerifyJwtException(response);
                return;
            }
        }

        chain.doFilter(request, response);
        */
    }

    // result 에 해당하는 메시지 조회
    private String getMessage(String result) {
        // 지역 설정 default 는 us
        // 아래 코드를 수정을 통해 다국어 지원 가능
        // filter 내에서는 MessageConfiguration 사용이 불가하기 때문에 locale 설정을 다시 해줘야만 함

        //return messageSource.getMessage(result, null, Locale.KOREAN);
        return messageSource.getMessage(result, null, Locale.US);

    }

    private void occurVerifyJwtException(ServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(responseService.getFailOrCommonResult(Integer.valueOf(getMessage("verifyJWTException.result")), getMessage("verifyJWTException.msg"))));
        out.flush();

        return;
    }

    private void occurLoadPublicKeyException(ServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter out = response.getWriter();
        out.print(mapper.writeValueAsString(responseService.getFailOrCommonResult(Integer.valueOf(getMessage("loadPublicKeyException.result")), getMessage("loadPublicKeyException.msg"))));
        out.flush();

        return;
    }
}
