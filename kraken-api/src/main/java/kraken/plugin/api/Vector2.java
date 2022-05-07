package kraken.plugin.api;

import java.util.Objects;

/**
 * A vector2.
 */
public class Vector2 {

    public static final Vector2 ZERO = new Vector2(0.0f, 0.0f);
    public static final Vector2 ONE = new Vector2(1.0f, 1.0f);

    private float x;
    private float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public Vector2 setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector2 setY(float y) {
        this.y = y;
        return this;
    }

    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(float a) {
        return add(a, a);
    }

    public Vector2 add(Vector2 v) {
        return add(v.x, v.y);
    }

    public Vector2 sub(float x, float y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2 sub(float a) {
        return sub(a, a);
    }

    public Vector2 sub(Vector2 v) {
        return sub(v.x, v.y);
    }

    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2 mul(float a) {
        return mul(a, a);
    }

    public Vector2 mul(Vector2 v) {
        return mul(v.x, v.y);
    }

    public Vector2 div(float x, float y) {
        this.x /= x;
        this.y /= y;
        return this;
    }

    public Vector2 div(float a) {
        return div(a, a);
    }

    public Vector2 div(Vector2 v) {
        return div(v.x, v.y);
    }

    /**
     * Calculates the distance between 2 vectors.
     */
    public float distance(Vector2 other) {
        return (float) Math.sqrt(Math.pow(other.getX() - x, 2) + Math.pow(other.getY() - y, 2));
    }

    /**
     * Normalizes this vector into a new vector.
     */
    public Vector2 normalize() {
        double length = Math.sqrt(getX() * getX() + getY() * getY());
        float x = 0.f;
        float y = 0.f;
        if (length != 0) {
            x = (float) (getX() / length);
            y = (float) (getY() / length);
        }

        return new Vector2(x, y);
    }

    /**
     * Converts a scene coordinate to a tile coordinate.
     */
    public Vector2i toTile() {
        return new Vector2i((int)(x / 512.f), (int)(y / 512.f));
    }

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2 vector2 = (Vector2) o;
        return Float.compare(vector2.x, x) == 0 &&
                Float.compare(vector2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
