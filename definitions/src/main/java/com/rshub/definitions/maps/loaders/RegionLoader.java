package com.rshub.definitions.maps.loaders;

import com.rshub.definitions.maps.MapObject;
import com.rshub.definitions.maps.ObjectType;
import com.rshub.definitions.maps.RegionDefinition;
import com.rshub.definitions.maps.WorldTile;
import com.rshub.utilities.ByteBufferKt;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class RegionLoader {

    public RegionDefinition load(int id, ByteBuffer objectsBuffer, ByteBuffer tilesBuffer) {
        RegionDefinition def = newDefinition(id);
        if(objectsBuffer != null) {
            decodeObjects(id, def, objectsBuffer);
        }
        if(tilesBuffer != null) {
            for (int plane = 0; plane < 4; plane++) {
                for (int x = 0; x < 64; x++) {
                    for (int y = 0; y < 64; y++) {
                        int flags = tilesBuffer.get() & 0xff;
                        if ((flags & 0x1) != 0) {
                            int shapeHash = tilesBuffer.get() & 0xff;
                            def.overlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(tilesBuffer);
                            def.overlayPathShapes[plane][x][y] = (byte) (shapeHash >> 2);
                            def.overlayRotations[plane][x][y] = (byte) (shapeHash & 0x3);
                        }
                        if ((flags & 0x2) != 0) {
                            def.settings[plane][x][y] = (byte) (tilesBuffer.get() & 0xff);
                        }
                        if ((flags & 0x4) != 0) {
                            def.underlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(tilesBuffer);
                        }
                        if ((flags & 0x8) != 0) {
                            // tile heights (unsigned)
                            tilesBuffer.get();
                        }
                    }
                }
            }
        }
        return def;
    }

    private void decodeObjects(int regionId, RegionDefinition def, ByteBuffer stream) {
        if(stream.capacity() == 0) {
            System.out.println("No objects data for region " + regionId);
            return;
        }
        int regionX = regionId >> 8;
        int regionY = regionId & 0xff;

        int objectId = -1;
        int incr;
        while ((incr = ByteBufferKt.getSmartSizeVar(stream)) != 0) {
            objectId += incr;
            int location = 0;
            int incr2;
            while ((incr2 = ByteBufferKt.getUnsignedSmart(stream)) != 0) {
                location += incr2 - 1;
                int localX = (location >> 6 & 0x3f);
                int localY = (location & 0x3f);
                int plane = location >> 12;
                int objectData = stream.get() & 0xff;
                boolean flag = (objectData & 0x80) != 0;
                int type = objectData >> 2 & 0x1f;
                int rotation = objectData & 0x3;
                int metaDataFlag = 0;
                if (flag) {
                    metaDataFlag = stream.get() & 0xff;
//						if (metaDataFlag != 0)
//							System.err.println("Metadata flag: " + metaDataFlag);
                    if ((metaDataFlag & 0x1) != 0) {
                        float f1 = 0.0F, f2 = 0.0F, f3 = 0.0F, f4 = 1.0F;
                        f1 = (float) stream.getShort() / 32768.0F;
                        f2 = (float) stream.getShort() / 32768.0F;
                        f3 = (float) stream.getShort() / 32768.0F;
                        f4 = (float) stream.getShort() / 32768.0F;
//							System.err.println("4 float flag: " + f1 + ", " + f2 + ", " + f3 + ", " + f4);
                    }
                    float f1 = 0.0f, f2 = 0.0f, f3 = 0.0f;
                    boolean print = false;
                    if ((metaDataFlag & 0x2) != 0) {
                        f1 = stream.getShort();
                        print = true;
                    }
                    if ((metaDataFlag & 0x4) != 0) {
                        f2 = stream.getShort();
                        print = true;
                    }
                    if ((metaDataFlag & 0x8) != 0) {
                        f3 = stream.getShort();
                        print = true;
                    }
//						if (print)
//							System.err.println("3 float flag: " + f1 + ", " + f2 + ", " + f3);

                    f1 = f2 = f3 = 1.0f;
                    print = false;
                    if ((metaDataFlag & 0x10) != 0) {
                        f1 = f2 = f3 = stream.getShort();
                        print = true;
                    } else {
                        if ((metaDataFlag & 0x20) != 0) {
                            f1 = stream.getShort();
                            print = true;
                        }
                        if ((metaDataFlag & 0x40) != 0) {
                            f2 = stream.getShort();
                            print = true;
                        }
                        if ((metaDataFlag & 0x80) != 0) {
                            f3 = stream.getShort();
                            print = true;
                        }
                    }
//						if (print)
//							System.err.println("3 float flag 2: " + f1 + ", " + f2 + ", " + f3);
                }
                int objectPlane = plane;
                if (def.settings != null && (def.settings[1][localX][localY] & 0x2) != 0)
                    objectPlane--;
                if (objectPlane < 0 || objectPlane >= 4 || plane < 0 || plane >= 4)
                    continue;
                if (ObjectType.forId(type) == null) {
                    System.err.println("Invalid object type: " + type + ", Object ID " + objectId);
                    continue;
                }
                MapObject obj = new MapObject(objectId,
                        localX + regionX * 64,
                        localY + regionY * 64,
                        objectPlane,
                        rotation,
                        ObjectType.forId(type),
                        new WorldTile(localX, localY, plane));
                def.getObjects().add(obj);
            }
        }
    }

    private void decodeTiles(int x, int y, int plane, ByteBuffer stream, RegionDefinition def) {
        int flags = stream.get() & 0xff;
        if ((flags & 0x1) != 0) {
            int shapeHash = stream.get() & 0xff;
            def.overlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(stream);
            def.overlayPathShapes[plane][x][y] = (byte) (shapeHash >> 2);
            def.overlayRotations[plane][x][y] = (byte) (shapeHash & 0x3);
        }
        if ((flags & 0x2) != 0) {
            def.settings[plane][x][y] = (byte) ByteBufferKt.getUnsignedByte(stream);
        }
        if ((flags & 0x4) != 0) {
            def.underlayIds[plane][x][y] = ByteBufferKt.getUnsignedSmart(stream);
        }
        if ((flags & 0x8) != 0) {
            // tile heights (unsigned)
            stream.get();
        }
    }

    @NotNull
    public RegionDefinition newDefinition(int id) {
        return new RegionDefinition(id);
    }
}
