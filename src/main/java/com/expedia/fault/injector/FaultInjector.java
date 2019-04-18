package com.expedia.fault.injector;

import java.util.concurrent.TimeUnit;

public interface FaultInjector {
    void cpu();

    void cpu(double targetCpuUtilization, int duration, TimeUnit unit);

    void disk();

    void memory();

    void resetMemoryFault();

    static FaultInjector createDefault() {
        return new DefaultFaultInjector();
    }
}
