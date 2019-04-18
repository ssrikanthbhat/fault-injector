package com.expedia.fault.injector.dropwizard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FaultInjectorConfig {
    @JsonProperty
    private boolean enableEndPoint;

    public boolean isEnableEndPoint() {
        return enableEndPoint;
    }
}
