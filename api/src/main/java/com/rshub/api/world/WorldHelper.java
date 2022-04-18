package com.rshub.api.world;

import com.rshub.api.entities.GroundItemManager;
import com.rshub.api.entities.SpiritManager;
import com.rshub.api.entities.WorldObjectManager;
import com.rshub.api.entities.items.WorldItem;
import com.rshub.api.entities.objects.WorldObject;
import com.rshub.api.entities.spirits.WorldSpirit;
import com.rshub.api.entities.spirits.npc.WorldNpc;
import com.rshub.api.entities.spirits.player.WorldPlayer;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Npcs;

import java.util.List;
import java.util.function.Predicate;

public final class WorldHelper {
    private WorldHelper() {
    }

    static final WorldObjectManager OBJECT_MANAGER = new WorldObjectManager();
    static final SpiritManager SPIRIT_MANAGER = new SpiritManager();
    static final GroundItemManager GROUND_ITEM_MANAGER = new GroundItemManager();

    public static WorldObject closestObjectIgnoreClip(Predicate<WorldObject> predicate) {
        return OBJECT_MANAGER.closestIgnoreClip(predicate::test);
    }

    public static WorldObject closestObject(Predicate<WorldObject> predicate) {
        return OBJECT_MANAGER.closest(predicate::test);
    }

    public static WorldItem closestGroundItem(Predicate<WorldItem> predicate) {
        return GROUND_ITEM_MANAGER.closest(predicate::test);
    }

    public static List<WorldItem> groundItems(Predicate<WorldItem> predicate) {
        return GROUND_ITEM_MANAGER.all(predicate::test);
    }

    public static WorldItem groundItemAt(int id, int x, int y) {
        List<WorldItem> list = GROUND_ITEM_MANAGER.all(worldItem -> worldItem.getId() == id && worldItem.getGlobalPosition().getX() == x && worldItem.getGlobalPosition().getY() == y);
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static WorldNpc closestNpc(Predicate<WorldNpc> predicate) {
        return SPIRIT_MANAGER.closestNpc(predicate::test);
    }

    public static WorldNpc byServerIndex(int index) {
        Npc npc = Npcs.byServerIndex(index);
        if(npc != null) {
            return new WorldNpc(npc);
        }
        return null;
    }

    public static WorldPlayer closestPlayer(Predicate<WorldPlayer> predicate) {
        return SPIRIT_MANAGER.closestPlayer(predicate::test);
    }
}
