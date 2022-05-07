package kraken.plugin.api;

/**
 * Provides array utilities.
 */
public final class Array {

    private Array() {
    }

    public static boolean contains(int[] array, int v) {
        for (int i : array) {
            if (i == v) {
                return true;
            }
        }
        return false;
    }

}
