package com.expedia.fault.injector.dropwizard;

import org.junit.Before;
import org.junit.Test;

import com.codahale.metrics.MetricRegistry;
import com.google.common.io.Resources;

import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.Environment;
import io.dropwizard.validation.BaseValidator;
import java.io.File;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FaultInjectorBundleTest {
    private Environment env;
    private DropwizardAppConfig exampleAppConfig;
    private YamlConfigurationFactory factory;

    @Before
    public void setUp() throws Exception {
        env = mock(Environment.class);
        factory = new YamlConfigurationFactory<>(DropwizardAppConfig.class, BaseValidator.newValidator(),
                Jackson.newObjectMapper(), "dw");

        when(env.jersey()).thenReturn(mock(JerseyEnvironment.class));
        when(env.metrics()).thenReturn(new MetricRegistry());
        when(env.lifecycle()).thenReturn(new LifecycleEnvironment());
        when(env.getApplicationContext()).thenReturn(new MutableServletContextHandler());

        exampleAppConfig = (DropwizardAppConfig) factory.build(resourceFileName("config.yml"));
        runBundle(new FaultInjectorBundle());
    }

    @Test
    public void ensureHeartBeatResourceIsInstalled() {
        verify(env.jersey(), times(1)).register(any(FaultInjectorResource.class));
    }

    private void runBundle(FaultInjectorBundle bundle) {
        bundle.run(exampleAppConfig, env);
    }

    private File resourceFileName(String resourceName) throws URISyntaxException {
        return new File(Resources.getResource(resourceName).toURI());
    }


}
