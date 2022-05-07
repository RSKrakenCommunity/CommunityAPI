package kraken.plugin.api;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides access to random numbers.
 */
public final class Rng {

    public static final int OPT_ALPHABET = (1 << 0);
    public static final int OPT_NUMERICAL = (1 << 1);
    public static final int OPT_SYMBOLS = (1 << 2);
    public static final int OPT_SPACES = (1 << 3);

    private static final String RANDOM_CHARS_ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private static final String RANDOM_CHARS_NUMERICAL = "0123456789";
    private static final String RANDOM_CHARS_SYMBOLS = "!@#$%^&*()_+-=[]{};'\\:\"|,./<>?";

    private Rng() {
    }

    public static boolean bool(Random r) {
        return r.nextBoolean();
    }

    public static boolean bool() {
        return bool(ThreadLocalRandom.current());
    }

    public static int i32(Random r) {
        return r.nextInt();
    }

    public static int i32() {
        return i32(ThreadLocalRandom.current());
    }

    public static int ui32(Random r) {
        return i32(r) & Integer.MAX_VALUE;
    }

    public static int ui32() {
        return ui32(ThreadLocalRandom.current());
    }

    public static int i32(Random r, int max) {
        if (max == 0) {
            return 0;
        }

        return ui32(r) % max;
    }

    public static int i32(int max) {
        if (max == 0) {
            return 0;
        }

        return ui32(ThreadLocalRandom.current()) % max;
    }

    public static int i32(Random r, int min, int max) {
        int range = max - min;
        if (range == 0) {
            return min;
        }

        return min + (ui32(r) % range);
    }

    public static int i32(int min, int max) {
        return i32(ThreadLocalRandom.current(), min, max);
    }

    public static long i64(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static float f32(Random r) {
        return r.nextFloat();
    }

    public static float f32() {
        return f32(ThreadLocalRandom.current());
    }

    public static double f32(float min, float max) {
        return (float) ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double f64() {
        return ThreadLocalRandom.current().nextDouble();
    }

    public static double f64(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static String string(Random r, int options, int length) {
        String selections = "";
        StringBuilder sb = new StringBuilder();
        if ((options & OPT_ALPHABET) == OPT_ALPHABET) {
            selections += RANDOM_CHARS_ALPHABET;
        }

        if ((options & OPT_NUMERICAL) == OPT_NUMERICAL) {
            selections += RANDOM_CHARS_NUMERICAL;
        }

        if ((options & OPT_SYMBOLS) == OPT_SYMBOLS) {
            selections += RANDOM_CHARS_SYMBOLS;
        }

        if ((options & OPT_SPACES) == OPT_SPACES) {
            selections += ' ';
        }

        if (selections.isEmpty()) {
            throw new NoOptionsException();
        }

        for (int i = 0; i < length; i++) {
            sb.append(selections.charAt(ui32(r) % selections.length()));
        }

        return sb.toString();
    }

    public static String string(int options, int length) {
        return string(ThreadLocalRandom.current(), options, length);
    }

    public static <T> T select(Random r, T[] arr) {
        if (arr.length == 0) {
            return null;
        }

        return arr[ui32(r) % arr.length];
    }

    public static <T> T select(T[] arr) {
        if (arr.length == 0) {
            return null;
        }

        return arr[ui32() % arr.length];
    }

    /**
     * Generates a number from 0-100, and determines if it falls within 0-chance.
     *
     * @param chance The chance of us returning true.
     * @return If the number is between 0 and chance.
     */
    public static boolean roll(int chance) {
        return i32() % 101 >= chance;
    }
}
