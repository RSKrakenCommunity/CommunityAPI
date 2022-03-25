package com.rshub.definitions.maps;

import com.rshub.definitions.Definition;
import kotlin.collections.MapsKt;
import kotlinx.serialization.json.JsonObject;
import org.jetbrains.annotations.NotNull;

public class MapTilesDefinition implements Definition {

    private int id;
    public int[][][] overlayIds;
    public int[][][] underlayIds;
    public byte[][][] overlayPathShapes;
    public byte[][][] overlayRotations;
    public byte[][][] tileFlags;

    public MapTilesDefinition(int id) {
        this.id = id;
        overlayIds = new int[4][64][64];
        underlayIds = new int[4][64][64];
        overlayPathShapes = new byte[4][64][64];
        overlayRotations = new byte[4][64][64];
        tileFlags = new byte[4][64][64];
    }

    @Override
    public int getId() {
        return this.id;
    }

    @NotNull
    @Override
    public JsonObject toJsonObject() {
        return new JsonObject(MapsKt.emptyMap());
    }
}
