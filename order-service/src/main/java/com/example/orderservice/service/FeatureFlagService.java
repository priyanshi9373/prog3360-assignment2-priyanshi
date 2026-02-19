package com.example.orderservice.service;

import io.getunleash.Unleash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {
    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);
    private final Unleash unleash;

    private final String orderNotifications = "order-notifications";
    private final String bulkOrderDiscount = "bulk-order-discount";

    public FeatureFlagService(Unleash unleash)
    {
        this.unleash = unleash;
    }

    public boolean isOrderNotificationsOn() {
        boolean flagStatus = false;

        try {
            flagStatus = unleash.isEnabled(orderNotifications, false);
            if (flagStatus) {
                log.info("Order notifications flag is ON");
            } else {
                log.info("Order notifications flag is OFF");
            }
        }
        catch(Exception e){
                log.warn("Cannot connect to Unleash, order notification is Off");
        }
        return flagStatus;
    }

    public boolean isBulkOrderDiscountOn() {
        boolean flagStatus = false;
        try {
            flagStatus = unleash.isEnabled(bulkOrderDiscount, false);
            if(flagStatus) {
            log.info("Bulk order discount flag is ON");
            } else{
                log.info("Bulk order discount flag is OFF");
            }
        }catch(Exception e){
            log.warn("Cannot connect to Unleash, bulk order discount is Off");
        }
        return flagStatus;
    }
}
