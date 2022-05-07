package kraken.plugin.api;

/**
 * A stat
 */
public class Stat {

    private int index;
    private int current;
    private int max;
    private int xp;

    public Stat() {
    }

    public Stat(int index, int current, int max, int xp) {
        this.index = index;
        this.current = current;
        this.max = max;
        this.xp = xp;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "index=" + index +
                ", current=" + current +
                ", max=" + max +
                ", xp=" + xp +
                '}';
    }
}
