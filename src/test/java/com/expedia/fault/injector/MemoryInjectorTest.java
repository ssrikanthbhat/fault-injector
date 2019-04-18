package com.expedia.fault.injector;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class MemoryInjectorTest {

    @Test(timeout = 2000)
    public void shouldTerminateWithinDuration() {
        new MemoryFaultInjector(500, TimeUnit.MILLISECONDS).run();
    }
}
