package com.expedia.fault.injector.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class DropwizardAppConfig extends Configuration implements FaultInjectorBundleConfig {
    @JsonProperty
    private FaultInjectorConfig faultInjector;

    @Override
    public FaultInjectorConfig getFaultInjectorConfig() {
        return faultInjector;
    }
}



