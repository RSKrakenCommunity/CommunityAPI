package com.rshub.definitions.maps.loaders;

import com.rshub.definitions.maps.MapObject;
import com.rshub.definitions.maps.MapTilesDefinition;
import com.rshub.definitions.maps.ObjectTilesDefinition;
import com.rshub.definitions.maps.ObjectType;
import com.rshub.utilities.ByteBufferKt;

import java.nio.ByteBuffer;

public class ObjectTilesLoader {

    private final MapTilesDefinition tileDef;

    public ObjectTilesLoader(MapTilesDefinition tileDef) {
        this.tileDef = tileDef;
    }

    public ObjectTilesDefinition load(int regionId, ByteBuffer stream) {
        ObjectTilesDefinition def = new ObjectTilesDefinition(regionId);
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
                if (localX < 0 || localX >= 64 || localY < 0 || localY >= 64) {
                    System.err.println("Error tile out of range: " + localX + ", " + localY);
                    continue;
                }
                int objectPlane = plane;
                if (tileDef.tileFlags != null && (tileDef.tileFlags[1][localX][localY] & 0x2) != 0)
                    objectPlane--;
                if (objectPlane < 0 || objectPlane >= 4 || plane < 0 || plane >= 4)
                    continue;
                if (ObjectType.forId(type) == null) {
                    System.err.println("Invalid object type: " + type + ", Object ID " + objectId);
                    continue;
                }
                MapObject obj = new MapObject(objectId, localX + regionX * 64, localY + regionY * 64, objectPlane, rotation, ObjectType.forId(type));
                def.getObjects().add(obj);
            }
        }
        return def;
    }

}
