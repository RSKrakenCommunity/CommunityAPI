package kraken.plugin.api;

/**
 * Provides measurement of elapsed time in milliseconds.
 */
public class Stopwatch {

    private long startMs;

    /**
     * Creates and starts a new stopwatch.
     */
    public static Stopwatch startNew() {
        Stopwatch s = new Stopwatch();
        s.start();
        return s;
    }

    /**
     * Starts the timer.
     */
    public void start() {
        startMs = System.currentTimeMillis();
    }

    /**
     * Calculates the number of milliseconds since start was called.
     */
    public long elapsed() {
        return (System.currentTimeMillis() - startMs);
    }

    /**
     * Restarts the timer if the provided amount of milliseconds has passed.
     *
     * @return If the timer was reset.
     */
    public boolean startIfElapsed(long ms) {
        if (elapsed() >= ms) {
            start();
            return true;
        }

        return false;
    }
}
