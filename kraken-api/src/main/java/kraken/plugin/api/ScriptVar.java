package kraken.plugin.api;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A variable being used by a custom-format script in the RuneScape client.
 */
public class ScriptVar {

    private int id;
    private byte[] data;

    public ScriptVar() {
    }

    public ScriptVar(int id, byte[] data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    /**
     * Retrieves the value of this variable as a 32-bit integer.
     *
     * @return The value of this variable.
     */
    public int getValueInt() {
        return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    /**
     * Retrieves the value of this variable as a float.
     *
     * @return The value of this variable.
     */
    public float getValueFloat() {
        return ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
    }

    @Override
    public String toString() {
        return "ScriptVar{" +
                "id=" + id +
                ", value=" + getValueInt() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScriptVar scrVar = (ScriptVar) o;
        return id == scrVar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

