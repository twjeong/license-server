package com.penta.ps.license.license.service.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.ECPublicKey;
import java.util.Date;

@Service
//@RequiredArgsConstructor
public class VerifyJwt {
    // Request의 Header에서 token 파싱 : "PENTA-AUTH-TOKEN: jwt토큰"
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("PENTA-AUTH-TOKEN");
    }

    public String resolveCMToken(HttpServletRequest req) {
        return req.getHeader("CM-AUTH-TOKEN");
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token, ECPublicKey publicKey) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 예외 처리 추가 필요
            return false;
        }
    }

    // Jwt 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(MakeKey.getPublicKey()).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            // 예외 처리 추가 필요
            return false;
        }
    }
}
