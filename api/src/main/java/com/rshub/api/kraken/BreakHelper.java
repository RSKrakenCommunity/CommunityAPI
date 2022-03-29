package com.rshub.api.kraken;

import kraken.plugin.api.Kraken;

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
}
