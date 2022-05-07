package kraken.plugin.api;

import java.util.Objects;

import static kraken.plugin.api.Rng.i32;

/**
 * A 3-dimensional area of integers.
 */
public class Area3di {

    private Vector3i begin;
    private Vector3i end;

    public Area3di() {
    }

    public Area3di(Vector3i begin, Vector3i end) {
        this.begin = new Vector3i(begin);
        this.end = new Vector3i(end);
    }

    public Area3di(Area3di o) {
        this(o.begin, o.end);
    }

    /**
     * Determines if the provided vector is within this area.
     */
    public boolean contains(Vector3i v) {
        return v.getX() >= begin.getX() &&
                v.getX() <= end.getX() &&
                v.getY() >= begin.getY() &&
                v.getY() <= end.getY() &&
                v.getZ() >= begin.getZ() &&
                v.getZ() <= end.getZ();
    }

    /**
     * Determines if the provided entity is within this area.
     */
    public boolean contains(Entity entity) {
        return contains(entity.getGlobalPosition());
    }

    /**
     * Calculates the center of this area.
     */
    public Vector3i center() {
        return new Vector3i(
                begin.getX() + ((end.getX() - begin.getX()) / 2),
                begin.getY() + ((end.getY() - begin.getY()) / 2),
                begin.getZ() + ((end.getZ() - begin.getZ()) / 2));
    }

    /**
     * Calculates a random point within this area.
     */
    public Vector3i random() {
        return new Vector3i(
                begin.getX() + i32(end.getX() - begin.getX()),
                begin.getY() + i32(end.getY() - begin.getY()),
                begin.getZ() + i32(end.getZ() - begin.getZ())
        );
    }

    /**
     * Expands this area. The existing area will be modified.
     *
     * @return The existing area.
     */
    public Area3di expand(Vector3i amount) {
        begin.sub(amount);
        end.add(amount);
        return this;
    }

    public Vector3i getBegin() {
        return begin;
    }

    public Area3di setBegin(Vector3i begin) {
        this.begin = new Vector3i(begin);
        return this;
    }

    public Vector3i getEnd() {
        return end;
    }

    public Area3di setEnd(Vector3i end) {
        this.end = new Vector3i(end);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area3di area2di = (Area3di) o;
        return Objects.equals(begin, area2di.begin) &&
                Objects.equals(end, area2di.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}
