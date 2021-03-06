package com.rshub.api.definitions;

import com.rshub.definitions.InventoryDefinition;
import com.rshub.definitions.ItemDefinition;
import com.rshub.definitions.NpcDefinition;
import com.rshub.definitions.VarbitDefinition;
import com.rshub.definitions.loaders.*;
import com.rshub.definitions.maps.RegionDefinition;
import com.rshub.definitions.objects.ObjectDefinition;
import com.rshub.filesystem.Filesystem;
import com.rshub.filesystem.sqlite.SqliteFilesystem;
import kotlin.jvm.functions.Function1;
import kraken.plugin.api.Client;
import kraken.plugin.api.ConVar;

import java.nio.file.Paths;
import java.util.List;

public final class CacheHelper {
    private CacheHelper() {
    }

    private static final String CACHE_PATH = "C:\\ProgramData\\Jagex\\RuneScape";
    private static final Filesystem FS = new SqliteFilesystem(Paths.get(CACHE_PATH));
    private static final DefinitionManager<InventoryDefinition> invManager = new DefinitionManager<>(FS, 2, 5, 0, new InventoryLoader());
    private static final DefinitionManager<VarbitDefinition> varbitManager = new DefinitionManager<>(FS, 2, 69, 0, new VarbitLoader());
    private static final DefinitionManager<ObjectDefinition> objectManager = new DefinitionManager<>(FS, 16, -1, 8, new ObjectLoader());
    private static final DefinitionManager<NpcDefinition> npcManager = new DefinitionManager<>(FS, 18, -1, 7, new NpcLoader());
    private static final DefinitionManager<ItemDefinition> itemManager = new DefinitionManager<>(FS, 19, -1, 8, new ItemLoader());
    private static final RegionManager REGION_MANAGER = new RegionManager(FS);

    public static InventoryDefinition getInventory(int id) {
        return invManager.get(id, false);
    }

    public static VarbitDefinition getVarbit(int id) {
        return varbitManager.get(id, false);
    }

    public static int getVarbitValue(int varbitId) {
        if(varbitId == -1) return 0;
        VarbitDefinition def = getVarbit(varbitId);
        int bits = (def.getMsb() - def.getLsb());
        ConVar convar = Client.getConVarById(def.getIndex());
        if(convar == null) return 0;
        if(bits == 0) {
            return convar.getValueMasked(def.getLsb(), 1);
        }
        return convar.getValueMasked(def.getLsb(), bits);
    }

    public static List<VarbitDefinition> varbits(Function1<VarbitDefinition, Boolean> filter) {
        return varbitManager.all(filter);
    }

    public static List<VarbitDefinition> findVarbitsFor(int convarId) {
        return varbitManager.all(def -> def.getIndex() == convarId);
    }

    public static ObjectDefinition getObject(int id) {
        return objectManager.get(id, false);
    }

    public static NpcDefinition getNpc(int id) {
        return npcManager.get(id, false);
    }

    public static ItemDefinition getItem(int id) {
        return itemManager.get(id, false);
    }

    public static RegionDefinition getRegion(int regionId) {
        return REGION_MANAGER.load(regionId);
    }

    public static int getMapArchiveId(int rx, int ry) {
        return rx | ry << 7;
    }

    public static Filesystem getFilesystem() {
        return FS;
    }
}
