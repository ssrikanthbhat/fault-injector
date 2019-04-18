## fault-injector

A Java based fault injection framework.

## Faults
* CPU fault injection - starts a thread that runs a tight loop that runs for a given duration
* Disk fault injection - starts a thread that creates 1G-file in the root file system. The files are named `fault-simulation-{uuid}.bin`.
* Memory fault injection - starts a thread that creates 10MB of `long` buffer added to a buffer list  and runs for a given duration

## CPU Fault Injection
```
new CpuFaultInjector(60, TimeUnit.SECONDS).run()
```

## Memory Fault Injection
```
new MemoryFaultInjector(60, TimeUnit.SECONDS).run()
```

## Disk Fault Injection
```
new DiskFaultInjector(ONE_GIGABYTE, new File("/").run()
```

## Endpoints
```
curl -X POST http://localhost:8080/__fault/cpu
curl -X POST http://localhost:8080/__fault/disk
curl -X POST http://localhost:8080/__fault/memory
```
