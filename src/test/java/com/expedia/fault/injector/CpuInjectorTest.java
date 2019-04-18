package com.expedia.fault.injector;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CpuInjectorTest {

    @Test(timeout = 1000)
    public void shouldTerminateWithinDuration() {
        new CpuFaultInjector(500, TimeUnit.MILLISECONDS).run();
    }
}
