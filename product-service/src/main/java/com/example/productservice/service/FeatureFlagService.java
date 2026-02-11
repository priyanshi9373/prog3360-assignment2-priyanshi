package com.example.productservice.service;

import io.getunleash.Unleash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {
    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);
    private final Unleash unleash;

    public FeatureFlagService(Unleash unleash) {
        this.unleash = unleash;
    }

    public boolean isPremiumPricingEnabled() {
        try {
            return unleash.isEnabled("premium-pricing", false); // fallback OFF
        } catch (Exception e) {
            log.warn("Unleash unavailable; premium-pricing fallback OFF", e);
            return false;
        }
    }
}
