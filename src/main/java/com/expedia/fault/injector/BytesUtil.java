package com.expedia.fault.injector;

public final class BytesUtil {

    private BytesUtil() {
    }

    public static String humanReadable(long bytes) {
        final int unit = 1024;
        if (bytes < unit) {
            return bytes + " B";
        }
        final int exp = (int) (Math.log(bytes) / Math.log(unit));
        final String unitName = "KMGTPE".charAt(exp - 1) + "i";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), unitName);
    }

}
