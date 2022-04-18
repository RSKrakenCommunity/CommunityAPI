package com.rshub.api.world;

import com.rshub.api.entities.spirits.npc.WorldNpc;
import com.rshub.api.entities.spirits.player.WorldPlayer;
import com.rshub.api.variables.Variables;

import java.util.List;

import static com.rshub.api.world.WorldHelper.SPIRIT_MANAGER;

public final class PlayerHelper {
    private PlayerHelper() {}

    /**
     * Finds the npc who is attacking the local player
     * @return The Attacking entity
     */

    public static List<WorldNpc> getAttackingNpc() {
        return SPIRIT_MANAGER.findAttackingNpcs();
    }

    public static List<WorldPlayer> getAttackingPlayer() {
        return SPIRIT_MANAGER.findAttackingPlayers();
    }

    public static boolean isUnderAttack() {
        return !getAttackingNpc().isEmpty() || !getAttackingPlayer().isEmpty();
    }

    public static boolean isInCombat() {
        return Variables.IN_COMBAT.getValue() == 1;
    }

}
