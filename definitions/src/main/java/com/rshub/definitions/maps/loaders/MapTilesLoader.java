package com.rshub.definitions.maps.loaders;

import com.rshub.definitions.maps.MapTilesDefinition;
import com.rshub.utilities.ByteBufferKt;

import java.nio.ByteBuffer;

public class MapTilesLoader {

    public MapTilesDefinition load(int regionId, ByteBuffer buffer) {
        MapTilesDefinition def = new MapTilesDefinition(regionId);
        System.out.println("Cap " + buffer.capacity());
        if(buffer.capacity() == 0) return def;
        for (int plane = 0; plane < 4; plane++) {
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 64; y++) {
                    decodeTiles(regionId, x, y, plane, buffer, def);
                }
            }
        }

        return def;
    }

    private void decodeTiles(int regionId, int x, int y, int plane, ByteBuffer stream, MapTilesDefinition def) {
        int flags = stream.get() & 0xff;

        if ((flags & 0x10) != 0)
            System.err.println("Flag 0x10 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
        if ((flags & 0x20) != 0)
            System.err.println("Flag 0x20 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
        if ((flags & 0x40) != 0)
            System.err.println("Flag 0x40 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);
        if ((flags & 0x80) != 0)
            System.err.println("Flag 0x80 found for tile (" + x + ", " + y + ", " + plane + ") at region " + regionId);

        if ((flags & 0x1) != 0) {
            int shapeHash = stream.get() & 0xff;
            def.overlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(stream);
            def.overlayPathShapes[plane][x][y] = (byte) (shapeHash >> 2);
            def.overlayRotations[plane][x][y] = (byte) (shapeHash & 0x3);
        }
        if ((flags & 0x2) != 0) {
            def.tileFlags[plane][x][y] = (byte) (stream.get() & 0xff);
        }
        if ((flags & 0x4) != 0) {
            def.underlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(stream);
        }
        if ((flags & 0x8) != 0) {
            // tile heights (unsigned)
            stream.get();
        }
    }

}
