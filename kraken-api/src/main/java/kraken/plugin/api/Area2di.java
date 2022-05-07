package kraken.plugin.api;

import java.util.Objects;

import static kraken.plugin.api.Rng.ui32;

/**
 * A 2-dimensional area of integers.
 */
public class Area2di {

    private Vector2i begin;
    private Vector2i end;

    public Area2di() {
    }

    public Area2di(Vector2i begin, Vector2i end) {
        this.begin = new Vector2i(begin);
        this.end = new Vector2i(end);
    }

    public Area2di(Area2di o) {
        this(o.begin, o.end);
    }

    /**
     * Determines if the provided vector is within this area.
     */
    public boolean contains(Vector2i v) {
        return v.getX() >= begin.getX() &&
                v.getX() <= end.getX() &&
                v.getY() >= begin.getY() &&
                v.getY() <= end.getY();
    }

    /**
     * Determines if the provided vector is within this area.
     */
    public boolean contains(Vector3i v) {
        return contains(new Vector2i(v));
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
    public Vector2i center() {
        return new Vector2i(
                begin.getX() + ((end.getX() - begin.getX()) / 2),
                begin.getY() + ((end.getY() - begin.getY()) / 2));
    }

    /**
     * Calculates a random point within this area.
     */
    public Vector2i random() {
        return new Vector2i(
                begin.getX() + (ui32() % (end.getX() - begin.getX())),
                begin.getY() + (ui32() % (end.getY() - begin.getY()))
                );
    }

    /**
     * Shrinks this area. The existing area will be modified.
     *
     * @return The existing area.
     */
    public Area2di shrink(Vector2i amount) {
        begin.add(amount);
        end.sub(amount);
        return this;
    }

    /**
     * Expands this area. The existing area will be modified.
     *
     * @return The existing area.
     */
    public Area2di expand(Vector2i amount) {
        begin.sub(amount);
        end.add(amount);
        return this;
    }

    public Vector2i getBegin() {
        return begin;
    }

    public Area2di setBegin(Vector2i begin) {
        this.begin = new Vector2i(begin);
        return this;
    }

    public Vector2i getEnd() {
        return end;
    }

    public Area2di setEnd(Vector2i end) {
        this.end = new Vector2i(end);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area2di area2di = (Area2di) o;
        return Objects.equals(begin, area2di.begin) &&
                Objects.equals(end, area2di.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }
}
