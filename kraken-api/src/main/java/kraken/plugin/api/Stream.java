package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple byte stream implementation providing data encoding compatible
 * with the RuneScape protocol.
 * <p>
 * A single position is used for both reading and writing.
 * <p>
 * TODO FIXME finish this, we don't really need it right now
 */
public class Stream {

    /**
     * The byte buffer to access.
     */
    private final byte[] buffer;

    /**
     * The position to read/write at.
     */
    private int position = 0;

    public Stream(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * Writes a single byte to the stream, and moves the position forward.
     */
    public void writeByte(int b) {
        buffer[position++] = (byte) b;
    }

    /**
     * Reads a single byte from the stream, and moves the position forward.
     */
    public int readByte() {
        return buffer[position++];
    }

    /**
     * Reads a single byte from the stream, and moves the position forward.
     */
    public int readByteUnsigned() {
        return buffer[position++] & 0xff;
    }

    /**
     * Writes a single short to the stream, and moves the position forward.
     */
    public void writeShort(int b) {
        writeByte((b >>> 8) & 0xff);
        writeByte(b & 0xff);
    }

    /**
     * Reads a single short from the stream, and moves the position forward.
     */
    public int readShort() {
        // casting to short is enough, no need to do any weird shit
        return (short) readShortUnsigned();
    }

    /**
     * Reads a single short from the stream, and moves the position forward.
     */
    public int readShortUnsigned() {
        return (readByteUnsigned() << 8) | readByteUnsigned();
    }

    /**
     * Reads 3 bytes from the stream, and moves the position forward.
     */
    public int readTri() {
        return (readByteUnsigned() << 16) | (readByteUnsigned() << 8) | readByteUnsigned();
    }


    /**
     * Writes a single 32 bit integer to the stream, and moves the position forward.
     */
    public void writeInt(int b) {
        writeByte((b >> 24) & 0xff);
        writeByte((b >> 16) & 0xff);
        writeByte((b >> 8) & 0xff);
        writeByte(b & 0xff);
    }

    /**
     * Reads a 32 bit integer from the stream, and moves the position forward.
     */
    public int readInt() {
        return (readByteUnsigned() << 24) | (readByteUnsigned() << 16) | (readByteUnsigned() << 8) | (readByteUnsigned());
    }

    /**
     * Reads the lower significant bits of a variable data type.
     */
    public int readVariableLsb(int f, int s, boolean signed) {
        int n = f & ~0x80;
        for (int i = 1; i < s; i++) {
            n = (n << 8) | readByteUnsigned();
        }

        if (signed) {
            n -= (1 << s * 8 - 2);
        }
        return n;
    }

    /**
     * Reads a variable sized data type.
     */
    public int readVariable() {
        int f = readByteUnsigned();
        int v = 0;
        if ((f & 0x80) != 0x80) {
            v = readVariableLsb(f, 2, false);
        } else {
            v = readVariableLsb(f, 4, false);
        }
        return v;
    }

    /**
     * Reads a string.
     */
    public String readString() {
        StringBuilder bldr = new StringBuilder();
        int read;
        while ((read = readByteUnsigned()) != 0) {
            bldr.append((char) read);
        }
        return bldr.toString();
    }

    /**
     * Reads an object map from the stream.
     * <p>
     * TODO FIXME this needs more work..
     */
    public Map<Integer, Object> readObjectMap() {
        Map<Integer, Object> map = new HashMap<>();
        int s = readByteUnsigned();
        for (int i = 0; i < s; i++) {
            Object v = null;
            int type = readByteUnsigned();
            int key = readTri();
            switch (type) {
                case 0:
                    v = readInt();
                    break;
                case 1:
                    v = readString();
                    break;
                default:
                    throw new UnsupportedOperationException();
            }

            map.put(key, v);
        }
        return map;
    }

    /**
     * Changes the read/write position of this stream.
     */
    public void position(int pos) {
        this.position = pos;
    }

    /**
     * Retrieves the position of the stream.
     */
    public int position() {
        return position;
    }

    /**
     * Determines if there are any bytes remaining in the stream.
     */
    public boolean hasRemaining() {
        return this.position < buffer.length;
    }

    /**
     * Determines if there are enough remaining bytes in the stream.
     */
    public boolean hasRemaining(int count) {
        return buffer.length - this.position >= count;
    }
}
