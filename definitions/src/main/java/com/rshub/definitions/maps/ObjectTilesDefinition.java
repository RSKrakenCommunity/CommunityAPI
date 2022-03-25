package com.rshub.definitions.maps;

import com.rshub.definitions.Definition;
import kotlin.collections.MapsKt;
import kotlinx.serialization.json.JsonObject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ObjectTilesDefinition implements Definition {

    private final int regionId;
    private List<MapObject> objects;

    public ObjectTilesDefinition(int regionId) {
        this.regionId = regionId;
        this.objects = new ArrayList<>();
    }

    public List<MapObject> getObjects() {
        return objects;
    }

    @Override
    public int getId() {
        return this.regionId;
    }

    @NotNull
    @Override
    public JsonObject toJsonObject() {
        return new JsonObject(MapsKt.emptyMap());
    }
}
