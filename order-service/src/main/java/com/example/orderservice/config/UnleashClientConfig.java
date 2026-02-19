package com.example.orderservice.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnleashClientConfig {

    @Value("${unleash.api-url}")
    private String apiUrl;

    @Value("${unleash.api-token}")
    private String apiToken;

    @Value("${unleash.app-name}")
    private String appName;

    @Value("${unleash.instance-id}")
    private String instanceId;

    @Value("${unleash.environment}")
    private String environment;

    @Bean
    public Unleash unleashClient()
    {
        UnleashConfig unleashConfig = UnleashConfig.builder()
                .appName(appName)
                .instanceId(instanceId)
                .unleashAPI(apiUrl)
                .apiKey(apiToken)
                .environment(environment)
                .build();
        return  new DefaultUnleash(unleashConfig);
    }
}