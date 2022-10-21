package com.penta.ps.license.license.service.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.UUID;

@Service
public class MakeJwt extends com.penta.ps.license.license.service.common.MakeKey {
    public MakeJwt() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        super();
        // TODO Auto-generated constructor stub
    }

    private static final String ALG = "ES256";
    private static final String TYP ="JWT";
    private static final String KID = UUID.randomUUID().toString();
    private static final String ISS = "pentasecurity.com";

    public static String createJwt() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        final Date IAT_DATE = new Date();
        Date expDate = new Date(System.currentTimeMillis() + 3600000); //토큰 만료 시간 (60 분)

        return Jwts.builder()
                .setHeaderParam("kid", KID)
                .setHeaderParam("typ", TYP)
                .setHeaderParam("alg", ALG)
                .setIssuer(ISS)
                .setIssuedAt(IAT_DATE)
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.ES256, com.penta.ps.license.license.service.common.MakeKey.getPrivateKey())
                .compact();
    }
}
