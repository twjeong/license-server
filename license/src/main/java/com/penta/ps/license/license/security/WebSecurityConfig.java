package com.penta.ps.license.license.security;

import com.penta.ps.license.license.service.common.VerifyJwt;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	private final VerifyJwt verifyJwt;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http//.httpBasic().disable()    // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
		    .csrf().disable()    // csrf 보안 토큰 disable처리.
		    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
			.and()
			.authorizeRequests()    // 요청에 대한 사용권한 체크
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/findall/**").hasRole("USER")
			.antMatchers("/user/**").hasRole("USER")
			.antMatchers("/**").permitAll();    // 그외 나머지 요청은 누구나 접근 가능
/*
			// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
		    .and()
			.addFilterBefore(TestFilter(), UsernamePasswordAuthenticationFilter.class);
*/
	}
}
