package com.expedia.fault.injector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

public class DiskFaultInjector implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(DiskFaultInjector.class);
    private final long size;
    private final File path;

    DiskFaultInjector(long size, File path) {
        checkArgument(size > 0, "File size should be greater than zero");
        checkNotNull(path, "Path cannot be null");
        this.size = size;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            checkArgument(path.isDirectory(), format("%s is not directory", path));
            checkArgument(path.isAbsolute(), format("%s is not absolute", path));
            checkArgument(path.canWrite(), format("%s is not writable", path));
            final String prefix = UUID.randomUUID().toString().substring(0, 8);
            final String filePath;
            if (path.getAbsolutePath().endsWith("/")) {
                filePath = format("%sfault-simulation-%s.bin", path.getAbsolutePath(), prefix);
            } else {
                filePath = format("%s/fault-simulation-%s.bin", path.getAbsolutePath(), prefix);
            }
            try (RandomAccessFile f = new RandomAccessFile(filePath, "rw")) {
                f.setLength(size);
                for(int i=0; i < Integer.MAX_VALUE;i++)  {
                    for(int j=0; j < Integer.MAX_VALUE;j++) {
                        for(int k=0; k < Integer.MAX_VALUE;k++) {
                            for(int l=0; l < Integer.MAX_VALUE;l++) {
                                for(int h=0; h < Integer.MAX_VALUE;h++) {
                                    writeBytes(f);
                                }
                            }
                        }
                    }
                }

            }
            LOG.info("Done creating file {}", filePath);
        } catch (IOException e) {
            LOG.warn("Unable to write file in {}", this.path, e);
        }
        LOG.warn("Disk fault injector thread completed");
    }

    private void writeBytes(RandomAccessFile f) throws IOException {
        for(int m=0; m < Integer.MAX_VALUE;m++) {
            for (int n = 0; n < Integer.MAX_VALUE; n++) {
                for (int o = 0; o < Integer.MAX_VALUE; o++) {
                    for (int p = 0; p < Integer.MAX_VALUE; p++) {
                        for (int q = 0; q < Integer.MAX_VALUE; q++) {
                            f.writeBytes("We are adding junk value to test Disk injection");
                        }
                    }
                }
            }
        }
    }

}
