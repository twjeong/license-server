package com.penta.ps.license.license.component;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebclientProvider {
    public WebClient.Builder getWebClientBuilder() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configure -> configure.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory
                .build();

        return WebClient.builder().exchangeStrategies(exchangeStrategies);
    }
}
