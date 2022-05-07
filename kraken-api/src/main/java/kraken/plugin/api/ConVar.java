package kraken.plugin.api;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * A variable where the state is determined by the server.
 */
public class ConVar {

    public static final int ID_LOCAL_STATUS = 659;
    public static final int ID_COMBAT_MODE = 3711;
    public static final int ID_INTERFACE_MODE = 3814;
    public static final int ID_AUTO_RETALIATE = 462;
    public static final int ID_WEAPON_SHEATHE = 689;
    public static final int ID_WARNING_CLAN_WARS_SAFE = 446;
    public static final int ID_WARNING_CLAN_WARS_DANGEROUS = 447;
    public static final int ID_WITHDRAW_NOTE = 160;
    public static final int ID_RUNNING = 463;
    public static final int ID_PRIVACY = 4983;

    public static final int CFG_INTERFACE_MODE_NEW = 0;
    public static final int CFG_INTERFACE_MODE_LEGACY = 58;

    public static final int CFG_COMBAT_MODE_NEW = 64;
    public static final int CFG_COMBAT_MODE_LEGACY = 11328;

    public static final int CFG_AUTO_RETALIATE_ON = 0;
    public static final int CFG_AUTO_RETALIATE_OFF = 1;

    public static final int CFG_RUNNING_OFF = 0;
    public static final int CFG_RUNNING_ON = 1;

    public static final int CFG_WEAPON_SHEATHE_ACTIVE = 0;

    public static final int CFG_WARNING_CLAN_WARS_SAFE_ACTIVE = 0;
    public static final int CFG_WARNING_CLAN_WARS_DANGEROUS_ACTIVE = 1;

    public static final int CFG_WITHDRAW_NOTE_ACTIVE = 1;

    public static final int CFG_PRIVACY_ANYBODY = 0;
    public static final int CFG_PRIVACY_FRIENDS_ONLY = 67108864;
    public static final int CFG_PRIVACY_NOBODY = 134217728;

    private int id;
    private byte[] data;

    public ConVar() {
    }

    public ConVar(int id, byte[] data) {
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
     * Retrieves the value of this variable as a 32-bit integer. The result
     * will be shifted and masked based on the provided parameters. This may be useful
     * when 2 values are stored in one integer (e.g. ore boxes, health values.)
     *
     * @param off The offset to the bits.
     * @param bits The number of bits to read off.
     * @return The value of this variable.
     */
    public int getValueMasked(int off, int bits) {
        return (getValueInt() >> off) & Bits.BIT_TABLE[bits];
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
        return "ConVar{" +
                "id=" + id +
                ", value=" + getValueInt() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConVar conVar = (ConVar) o;
        return id == conVar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

