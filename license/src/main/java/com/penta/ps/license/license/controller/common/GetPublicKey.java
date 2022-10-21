package com.penta.ps.license.license.controller.common;

import com.penta.ps.license.license.component.WebclientProvider;
import com.penta.ps.license.license.dto.license.PublicKeyDto;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Service
//@RestController
@RequiredArgsConstructor
public class GetPublicKey {

    //private final WebClient.Builder webclientBuild;
    public static ECPublicKey PUBLICKEY;

    //@Value("${eureka-manager-url}")
    @Value("https://localhost:8762")
    private String eurekaBaseUrl;

    @Autowired
    private WebclientProvider webclientProvider;

    //@GetMapping("/publickey")
    public ECPublicKey getPublicKeyFromUI() throws NoSuchAlgorithmException, InvalidKeySpecException, SSLException {

        SslContext sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
        HttpClient httpClient = HttpClient.create().secure(builder -> builder.sslContext(sslContext));
        ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);

        /*
        Mono<PublicKeyDto> todo = webclientBuild.build()
                .get()
                .uri("https://localhost:8080/publickey")
                .retrieve()
                .bodyToMono(PublicKeyDto.class);
        */

        Mono<PublicKeyDto> todo = webclientProvider.getWebClientBuilder()
                .clientConnector(clientHttpConnector)
                .build()
                .get()
                //.uri("https://localhost:8080/publickey")
                .uri(eurekaBaseUrl + "/publickey")
                .retrieve()
                .bodyToMono(PublicKeyDto.class);

        String base64PublicKey = todo.block().getPublicKey();

        final KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PUBLICKEY = (ECPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey)));

        return PUBLICKEY;

        //return todo.block().getPublicKey();

    }
}
