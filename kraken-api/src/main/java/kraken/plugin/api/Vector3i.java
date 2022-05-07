package kraken.plugin.api;

import java.util.Objects;

/**
 * A vector3 made up of integers.
 */
public class Vector3i {

    public static final Vector3i ZERO = new Vector3i(0, 0, 0);
    public static final Vector3i ONE = new Vector3i(1, 1, 1);

    private int x;
    private int y;
    private int z;

    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i(Vector2i o) {
        this(o.getX(), o.getY(), 0);
    }

    public Vector3i(Vector3i o) {
        this(o.x, o.y, o.z);
    }

    public int getX() {
        return x;
    }

    public Vector3i setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Vector3i setY(int y) {
        this.y = y;
        return this;
    }

    public int getZ() {
        return z;
    }

    public Vector3i setZ(int z) {
        this.z = z;
        return this;
    }

    public Vector3i add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3i add(int a) {
        return add(a, a, a);
    }

    public Vector3i add(Vector2i v) {
        return add(v.getX(), v.getY(), 0);
    }

    public Vector3i add(Vector3i v) {
        return add(v.x, v.y, v.z);
    }

    public Vector3i sub(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3i sub(int a) {
        return sub(a, a, a);
    }

    public Vector3i sub(Vector3i v) {
        return sub(v.x, v.y, v.z);
    }

    public Vector3i mul(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3i mul(int a) {
        return mul(a, a, a);
    }

    public Vector3i mul(Vector3i v) {
        return mul(v.x, v.y, v.z);
    }

    public Vector3i div(int x, int y, int z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3i div(int a) {
        return div(a, a, a);
    }

    public Vector3i div(Vector3i v) {
        return div(v.x, v.y, v.z);
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public double distancePrecise(Vector2i other) {
        return Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public double distancePrecise(Vector3i other) {
        return Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2) + Math.pow(other.getZ() - z, 2));
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public int distance(Vector2i other) {
        return (int)distancePrecise(other);
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public int distance(Vector3i other) {
        return (int)distancePrecise(other);
    }

    /**
     * Expands this vector into an area.
     */
    public Area3di expand(Vector3i amount) {
        return new Area3di(new Vector3i(x - amount.getX(), y - amount.getY(), z - amount.getZ()),
                new Vector3i(x + amount.getX(), y + amount.getY(), z + amount.getZ()));
    }

    /**
     * Converts a tile coordinate to a scene coordinate.
     */
    public Vector3 toScene() {
        return new Vector3((float)x * 512.f, 0, (float)y * 512.f);
    }

    @Override
    public String toString() {
        return "Vector3i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3i vector3i = (Vector3i) o;
        return x == vector3i.x &&
                y == vector3i.y &&
                z == vector3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
