package com.penta.ps.license.license.service.common;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Service
@Getter
public class MakeKey {
    private static ECPublicKey EC_PUBLIC_KEY;
    private static ECPrivateKey EC_PRIVATE_KEY;

    public MakeKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        if(EC_PRIVATE_KEY == null) {
            genKeyPair();
        }
    }

    /*
     * 키 생성 알고리즘으로 ECDSA(Elliptic Curve Digital Signature Algorithm, 타원곡선 디지털 서명 알고리즘) 중 하나인
     * ES256(P-256 + SHA256)을 사용한다.
     * 블록체인에서 사용하는 알고리즘이다.
     * 함수가 실행될 때마다 랜덤한 키가 계속해서 생성된다.
     */
    public static void genKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException{
        final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        final KeyPair keyPair = keyPairGenerator.generateKeyPair();

        final KeyFactory keyFactory = KeyFactory.getInstance("EC");
        EC_PUBLIC_KEY = (ECPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyPair.getPublic().getEncoded()));
        EC_PRIVATE_KEY = (ECPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyPair.getPrivate().getEncoded()));
    }

    public static ECPublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        if(EC_PUBLIC_KEY == null) {
            genKeyPair();
        }

        return EC_PUBLIC_KEY;
    }

    protected static ECPrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeySpecException {
        if(EC_PRIVATE_KEY == null) {
            genKeyPair();
        }

        return EC_PRIVATE_KEY;
    }
}
