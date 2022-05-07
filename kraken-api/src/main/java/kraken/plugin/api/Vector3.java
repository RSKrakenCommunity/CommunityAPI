package kraken.plugin.api;

import java.util.Objects;

/**
 * A vector3.
 */
public class Vector3 {

    public static final Vector3 ZERO = new Vector3(0.0f, 0.0f, 0.0f);
    public static final Vector3 ONE = new Vector3(1.0f, 1.0f, 1.0f);

    private float x;
    private float y;
    private float z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 o) {
        this(o.x, o.y, o.z);
    }

    public float getX() {
        return x;
    }

    public Vector3 setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector3 setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Vector3 setZ(float z) {
        this.z = z;
        return this;
    }

    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 add(float a) {
        return add(a, a, a);
    }

    public Vector3 add(Vector2 v) {
        return add(v.getX(), v.getY(), 0);
    }

    public Vector3 add(Vector3 v) {
        return add(v.x, v.y, v.z);
    }

    public Vector3 sub(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vector3 sub(float a) {
        return sub(a, a, a);
    }

    public Vector3 sub(Vector3 v) {
        return sub(v.x, v.y, v.z);
    }

    public Vector3 mul(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vector3 mul(float a) {
        return mul(a, a, a);
    }

    public Vector3 mul(Vector3 v) {
        return mul(v.x, v.y, v.z);
    }

    public Vector3 div(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3 div(float a) {
        return div(a, a, a);
    }

    public Vector3 div(Vector3 v) {
        return div(v.x, v.y, v.z);
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public float distance(Vector3 other) {
        return (float) Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2) + Math.pow(other.getZ() - z, 2));
    }

    /**
     * Normalizes this vector into a new vector.
     */
    public Vector3 normalize() {
        double length = Math.sqrt(getX() * getX() + getY() * getY() + getZ() * getZ());
        float x = 0.f;
        float y = 0.f;
        float z = 0.f;
        if (length != 0) {
            x = (float) (getX() / length);
            y = (float) (getY() / length);
            z = (float) (getZ() / length);
        }

        return new Vector3(x, y, z);
    }

    /**
     * Converts a scene coordinate to a tile coordinate.
     */
    public Vector3i toTile() {
        return new Vector3i((int)(x / 512.f), (int)(z / 512.f), 0);
    }

    @Override
    public String toString() {
        return "Vector3{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Float.compare(vector3.x, x) == 0 &&
                Float.compare(vector3.y, y) == 0 &&
                Float.compare(vector3.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
