package com.expedia.fault.injector;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MemoryFaultInjector implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(MemoryFaultInjector.class);
    private static final int DEFAULT_ALLOCATION_SIZE = 10 * 1024 * 1024;
    private static final int DEFAULT_ALLOCATION_DELAY_MS = 1000;
    private final int duration;
    private final TimeUnit unit;

    public MemoryFaultInjector(int duration, TimeUnit unit) {
        this.duration = duration;
        this.unit = unit;
    }

    @Override
    public void run() {
        LOG.info("Injecting memory fault for {} seconds", unit.toSeconds(duration));
        final long endTime = unit.toNanos(duration);
        final long startTime = System.nanoTime();
        final int bufferSize = DEFAULT_ALLOCATION_SIZE;
        try {
            while ((System.nanoTime() - startTime) < endTime && !Thread.currentThread().isInterrupted()) {
                final long[] buffer = new long[bufferSize];
                LOG.warn("Allocated {}-byte buffer", BytesUtil.humanReadable(bufferSize));
                Globals.BUFFER_LIST.add(buffer);
                sleepFor(DEFAULT_ALLOCATION_DELAY_MS);
            }
        } catch (OutOfMemoryError e) {
            LOG.error(e.getMessage(), e);
        }
        LOG.warn("Memory fault injector thread done");
    }

    private void sleepFor(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            LOG.warn("Interrupted!");
        }
    }
}
