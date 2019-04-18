package com.expedia.fault.injector.dropwizard;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class DropwizardApp extends io.dropwizard.Application<DropwizardAppConfig> {

    public static void main(String[] args) throws Exception {
        new DropwizardApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<DropwizardAppConfig> bootstrap) {
        bootstrap.addBundle(new FaultInjectorBundle());
    }

    @Override
    public void run(DropwizardAppConfig config, Environment env) {
        env.jersey().register(new RootResource());
    }
}
