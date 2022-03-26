package com.rshub.api.world;

import com.rshub.api.entities.GroundItemManager;
import com.rshub.api.entities.SpiritManager;
import com.rshub.api.entities.WorldObjectManager;
import com.rshub.api.entities.items.WorldItem;
import com.rshub.api.entities.objects.WorldObject;
import com.rshub.api.entities.spirits.npc.WorldNpc;
import com.rshub.api.entities.spirits.player.WorldPlayer;
import com.rshub.definitions.maps.MapObject;
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

    public static WorldItem closestGroundItem(Predicate<WorldItem> predicate) {
        return GROUND_ITEM_MANAGER.closest(predicate::test);
    }

    public static WorldNpc closestNpc(Predicate<WorldNpc> predicate) {
        return SPIRIT_MANAGER.closestNpc(predicate::test);
    }

    public static WorldPlayer closestPlayer(Predicate<WorldPlayer> predicate) {
        return SPIRIT_MANAGER.closestPlayer(predicate::test);
    }
}
