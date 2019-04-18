package com.expedia.fault.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BoundedCpuFaultInjector implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(BoundedCpuFaultInjector.class);
    private static final int STEP_MILLIS = 100;
    private final double targetCpuUtilization;
    private final int duration;
    private final TimeUnit unit;
    private final CountDownLatch startLatch;

    public BoundedCpuFaultInjector(double targetCpuUtilization, int duration, TimeUnit unit,
                                   CountDownLatch startLatch) {
        Preconditions.checkArgument(targetCpuUtilization >= 0 && targetCpuUtilization <= 100,
                "Target cpu utilization be between 0 and 100, inclusive");
        this.targetCpuUtilization = targetCpuUtilization;
        this.duration = duration;
        this.unit = unit;
        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        LOG.info("Started spin loop thread for %{} %{}", duration, unit.toString());
        final long sleepTime = unit.toNanos(duration);
        final long startTime = System.nanoTime();
        try {
            startLatch.await();
            while ((System.nanoTime() - startTime) < sleepTime) {
                if (System.currentTimeMillis() % 100 == 0) {
                    final long delay = (long) (STEP_MILLIS * (1 - targetCpuUtilization / 100));
                    Thread.sleep(delay);
                }
            }
        } catch (InterruptedException e) {
            //nop
        }
        LOG.warn("Spin loop thread completed");
    }

}
