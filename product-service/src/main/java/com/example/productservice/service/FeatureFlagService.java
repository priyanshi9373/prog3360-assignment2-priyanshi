package com.example.productservice.service;

import io.getunleash.Unleash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {
    private static final Logger log = LoggerFactory.getLogger(FeatureFlagService.class);
    private final Unleash unleash;

    private final String premiumPricing = "premium-pricing";

    public FeatureFlagService(Unleash unleash)
    {
        this.unleash = unleash;
    }

    public boolean premiumPricingIsOn() {
        boolean flagStatus = false;
        try{
            flagStatus = unleash.isEnabled(premiumPricing, false);

            if(flagStatus)
            {
                log.info("premium pricing is on");
            }
            else
            {
                log.info("premium pricing is not on");
            }
        } catch(Exception e)
        {
            log.warn("Cannot connect to Unleash, premium pricing is off");
        }
        return flagStatus;
    }
}
