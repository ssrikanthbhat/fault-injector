package com.expedia.fault.injector;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FaultInjectorTest {

    @Test
    public void createDefault() {
        assertThat(FaultInjector.createDefault()).isInstanceOf(DefaultFaultInjector.class);
    }
}
