package com.expedia.fault.injector.dropwizard;

import com.expedia.fault.injector.DefaultFaultInjector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class FaultInjectorBundle<T extends Configuration & FaultInjectorBundleConfig> implements ConfiguredBundle<T> {
    private static final Logger LOG = LoggerFactory.getLogger(FaultInjectorBundle.class);

    @Override
    public void run(T config, Environment env) {
        if (config.getFaultInjectorConfig() == null) {
            LOG.warn("{} installed but no configuration was found!", FaultInjectorBundle.class.getSimpleName());
            return;
        }
        if (config.getFaultInjectorConfig().isEnableEndPoint()) {
            env.jersey().register(new FaultInjectorResource(new DefaultFaultInjector()));
        }
    }

    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        //nope
    }
}
