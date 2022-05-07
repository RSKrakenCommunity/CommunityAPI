package kraken.plugin.api;

/**
 * Provides time related utilities.
 */
public final class Time {

    private Time() {
    }

    /**
     * Calculates the amount per hour based on the total runtime and amount
     * already accumulated.
     */
    public static int perHour(long runtime, int amount) {
        return (int) ((double) amount * 3600000.0d / (double) runtime);
    }

    /**
     * Formats an elapsed time into a formatted readable string.
     *
     * @param runtime The elapsed time in milliseconds.
     * @return The formatted time sting.
     */
    public static String formatTime(long runtime) {
        long seconds = (runtime / 1000L) % 60L;
        long minutes = ((runtime / (1000L * 60L)) % 60L);
        long hours = ((runtime / (1000L * 60L * 60L) % 24L));
        long days = ((runtime / (1000L * 60L * 60L * 24L)));
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    }

    /**
     * Waits for the specified number of milliseconds. This should not be done
     * under rendering threads.
     *
     * @param ms The number of milliseconds to wait for.
     */
    public static void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sleeps until the condition is met or we timeout.
     */
    public static boolean waitUntil(Condition condition, long timeout, long delay) {
        long begin = System.currentTimeMillis();
        boolean met = false;
        try {
            while (System.currentTimeMillis() < (begin + timeout) && !(met = condition.met())) {
                waitFor(delay);
            }
        } catch (Throwable t) {
            Debug.printStackTrace("waiting for condition", t);
        }
        return met;
    }

    /**
     * Sleeps until the condition is met or we timeout.
     */
    public static boolean waitUntil(Condition condition, long timeout) {
        return waitUntil(condition, timeout, 100);
    }

    /**
     * Sleeps until the condition is met or we timeout.
     */
    public static boolean waitUntil(Condition condition) {
        return waitUntil(condition, 5000, 600);
    }
}
