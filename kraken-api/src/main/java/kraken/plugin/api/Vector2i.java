package kraken.plugin.api;

import java.util.Objects;

/**
 * A vector2 made up of integers.
 */
public class Vector2i {

    public static final Vector2i ZERO = new Vector2i(0, 0);
    public static final Vector2i ONE = new Vector2i(1, 1);

    private int x;
    private int y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i v) {
        this(v.x, v.y);
    }

    public Vector2i(Vector3i v) {
        this(v.getX(), v.getY());
    }

    public int getX() {
        return x;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i add(int a) {
        return add(a, a);
    }

    public Vector2i add(Vector2i v) {
        return add(v.x, v.y);
    }

    public Vector2i sub(int x, int y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2i sub(int a) {
        return sub(a, a);
    }

    public Vector2i sub(Vector2i v) {
        return sub(v.x, v.y);
    }

    public Vector2i mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2i mul(int a) {
        return mul(a, a);
    }

    public Vector2i mul(Vector2i v) {
        return mul(v.x, v.y);
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public int distance(Vector2i other) {
        return (int) Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public int distance(Vector3i other) {
        return (int) Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    /**
     * Expands this vector into an area.
     */
    public Area2di expand(Vector2i amount) {
        return new Area2di(new Vector2i(x - amount.getX(), y - amount.getY()),
                new Vector2i(x + amount.getX(), y + amount.getY()));
    }

    /**
     * Converts a tile coordinate to a scene coordinate.
     */
    public Vector2 toScene() {
        return new Vector2((float)x * 512.f, (float)y * 512.f);
    }

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2i vector2i = (Vector2i) o;
        return x == vector2i.x &&
                y == vector2i.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
