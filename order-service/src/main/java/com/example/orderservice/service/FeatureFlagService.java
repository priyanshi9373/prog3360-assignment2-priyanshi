package com.example.orderservice.service;

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

    public boolean isOrderNotificationsEnabled() {
        try {
            return unleash.isEnabled("order-notifications", false);
        } catch (Exception e) {
            log.warn("Unleash unavailable; order-notifications fallback OFF", e);
            return false;
        }
    }

    public boolean isBulkOrderDiscountEnabled() {
        try {
            return unleash.isEnabled("bulk-order-discount", false);
        } catch (Exception e) {
            log.warn("Unleash unavailable; bulk-order-discount fallback OFF", e);
            return false;
        }
    }
}
