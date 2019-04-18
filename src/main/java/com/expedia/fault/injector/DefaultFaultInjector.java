package com.expedia.fault.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;


public class DefaultFaultInjector implements FaultInjector {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultFaultInjector.class);
    private static final long ONE_GIGABYTE = 1024 * 1024 * 1024;
    private final ExecutorService executors = Executors.newCachedThreadPool();

    @Override
    public void cpu() {
        executors.submit(new CpuFaultInjector(60, TimeUnit.SECONDS));
    }

    @Override
    public void cpu(double targetCpuUtilization, int duration, TimeUnit unit) {
        checkArgument(targetCpuUtilization >= 0 && targetCpuUtilization <= 100,
                "Target cpu utilization be between 0 and 100, inclusive");
        checkArgument(duration > 0, "Duration must be > 0");

        LOG.info("Target CPU utilization is {}, duration={} {}", targetCpuUtilization, duration, unit);
        final int numProcessors = Runtime.getRuntime().availableProcessors();
        final CountDownLatch startLatch = new CountDownLatch(1);
        for (int a = 0; a < numProcessors; a++) {
            executors.submit(new BoundedCpuFaultInjector(targetCpuUtilization, duration, unit, startLatch));
        }
        startLatch.countDown();
    }

    @Override
    public void disk() {
        executors.submit(new DiskFaultInjector(ONE_GIGABYTE, new File("/var/tmp")));
    }

    @Override
    public void memory() {
        executors.submit(new MemoryFaultInjector(60, TimeUnit.SECONDS));
    }

    @Override
    @SuppressWarnings("PMD.DoNotCallGarbageCollectionExplicitly")
    public void resetMemoryFault() {
        Globals.BUFFER_LIST.clear();
        System.gc();
    }
}


