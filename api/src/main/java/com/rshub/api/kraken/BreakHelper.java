package com.rshub.api.kraken;

import kraken.plugin.api.Kraken;
import kraken.plugin.api.Rng;

import java.util.concurrent.TimeUnit;

public final class BreakHelper {
    private BreakHelper() {
    }

    public static void cancel() {
        Kraken.takeBreak(1000);
    }

    public static void takeBreak(long time, TimeUnit unit) {
        Kraken.takeBreak(unit.toMillis(time));
    }

    public static void takeRandomBreak(long min, long max, TimeUnit tmin, TimeUnit mmin) {
        Kraken.takeBreak(Rng.i32((int) tmin.toMillis(min), (int) mmin.toMillis(max)));
    }

    public static void takeRandomBreak(long min, long max, TimeUnit unit) {
        takeBreak(Rng.i32((int) min, (int) max), unit);
    }
}
