package com.expedia.fault.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static java.lang.String.format;

public class CpuFaultInjector implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(CpuFaultInjector.class);
    private final int duration;
    private final TimeUnit unit;

    public CpuFaultInjector(int duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    public void run() {
        LOG.info(format("Started spin loop thread for %d %s", duration, unit.toString()));
        final long sleepTime = unit.toNanos(duration);
        final long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {
            //nop
        }
        LOG.warn("Spin loop thread completed");
    }

}
