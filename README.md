## fault-injector

A fault injection framework for Java apps.

## Supported Faults
* CPU fault injection 
* Disk fault injection 
* Memory fault injection

##### CPU Fault Injection
Run a busy loop thread for 60 seconds:
```
curl -X POST "http://localhost:8080/__fault/cpu
```

Runs busy loop threads (equal to the number of available processors) to achieve an approximated 70%
process cpu utilization for 60 seconds
```
curl -X POST "http://localhost:8080/__fault/cpu/bounded?target=70&duration=60"
```

##### Memory Fault Injection

Runs a thread that allocates `long` buffers for 60 seconds (non GC eligible):
```
curl -X POST "http://localhost:8080/__fault/memory"
```
Reset the memory fault (clears the buffer holder and triggers a `System.gc()`)

```
curl -X POST "http://localhost:8080/__fault/memory/reset"
```

##### Disk Fault Injection

Runs a thread that creates 1G file in `/var/tmp`
```
curl -X POST "http://localhost:8080/__fault/disk"
```

## Integrating
Maven:
```
<dependency>
    <groupId>com.expedia</groupId>
    <artifactId>fault-injector</artifactId>
    <version>{fault-injector.version}</version>
</dependency>
```

##### Dropwizard Apps
```
 override def initialize(bootstrap: Bootstrap[T]): Unit = {
    bootstrap.addBundle(new FaultInjectorBundle[ServiceConfig]())
  }
```

## Endpoints
```
curl -X POST "http://localhost:8080/__fault/cpu"
curl -X POST "http://localhost:8080/__fault/cpu/bounded?target=70&duration=60"
curl -X POST "http://localhost:8080/__fault/disk"
curl -X POST "http://localhost:8080/__fault/memory"
curl -X POST "http://localhost:8080/__fault/reset"
```

