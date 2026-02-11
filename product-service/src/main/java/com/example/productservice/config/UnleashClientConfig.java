package com.example.productservice.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UnleashClientConfig {

    @Bean
    public Unleash unleash(
            @Value("${unleash.api-url}") String apiUrl,
            @Value("${unleash.api-token}") String apiToken,
            @Value("${unleash.app-name}") String appName,
            @Value("${unleash.instance-id}") String instanceId,
            @Value("${unleash.environment}") String environment
    ) {
        UnleashConfig config = UnleashConfig.builder()
                .appName(appName)
                .instanceId(instanceId)
                .unleashAPI(apiUrl)
                .apiKey(apiToken)
                .environment(environment)
                .build();

        return new DefaultUnleash(config);
    }
}
