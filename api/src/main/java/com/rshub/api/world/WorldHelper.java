package com.rshub.api.world;

import com.rshub.api.entities.GroundItemManager;
import com.rshub.api.entities.SpiritManager;
import com.rshub.api.entities.WorldObjectManager;
import com.rshub.definitions.maps.WorldObject;
import kraken.plugin.api.GroundItem;
import kraken.plugin.api.Npc;
import kraken.plugin.api.Player;

import java.util.function.Predicate;

public final class WorldHelper {
    private WorldHelper() {
    }

    private static final WorldObjectManager OBJECT_MANAGER = new WorldObjectManager();
    private static final SpiritManager SPIRIT_MANAGER = new SpiritManager();
    private static final GroundItemManager GROUND_ITEM_MANAGER = new GroundItemManager();

    public static WorldObject closestObject(Predicate<WorldObject> predicate) {
        return OBJECT_MANAGER.closest(predicate::test);
    }

    public static GroundItem closestGroundItem(Predicate<GroundItem> predicate) {
        return GROUND_ITEM_MANAGER.closest(predicate::test);
    }

    public static Npc closestNpc(Predicate<Npc> predicate) {
        return SPIRIT_MANAGER.closestNpc(predicate::test);
    }

    public static Player closestPlayer(Predicate<Player> predicate) {
        return SPIRIT_MANAGER.closestPlayer(predicate::test);
    }
}
