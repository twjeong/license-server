package com.penta.ps.license.license.config;

import com.penta.ps.license.license.controller.common.GetPublicKey;
import com.penta.ps.license.license.filter.JwtFilter;
import com.penta.ps.license.license.service.common.ResponseService;
import com.penta.ps.license.license.service.common.VerifyJwt;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class FilterConfiguration {

    private final VerifyJwt verifyJwt;
    private final GetPublicKey getPublicKey;
    private final MessageSource messageSource;
    private final ResponseService responseService;

    // jwt 는 정상동작하나 alpha 버전에서는 일단 skip
    @Bean
    public FilterRegistrationBean jwtFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //filterRegistrationBean.setFilter(new JwtFilter(new VerifyJwt(), new GetPublicKey(new WebclientProvider().getWebClientBuilder()), responseService, messageSource));
        filterRegistrationBean.setFilter(new JwtFilter(verifyJwt, getPublicKey, responseService, messageSource));
        //filterRegistrationBean.setUrlPatterns(Arrays.asList("/licenses"));
        //filterRegistrationBean.addUrlPatterns("/specific-licenses");
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        filterRegistrationBean.addUrlPatterns("/contracts");
        filterRegistrationBean.addUrlPatterns("/specific-contracts");
        filterRegistrationBean.addUrlPatterns("/usage-time");
        filterRegistrationBean.addUrlPatterns("/specific-usage-time");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
