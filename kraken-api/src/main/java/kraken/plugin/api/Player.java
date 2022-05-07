package kraken.plugin.api;

import java.util.HashMap;
import java.util.Map;

/**
 * A player within the game world.
 */
public final class Player extends Spirit {

    private Map<EquipmentSlot, Integer> equipment = new HashMap<>();
    private int totalLevel;
    private int combatLevel;

    /**
     * Do not make instances of this.
     */
    private Player() {
    }

    /**
     * Determines if this player is under attack currently.
     *
     * @return If this player is under attack currently.
     */
    public boolean isUnderAttack() {
        return isStatusBarActive(STATUS_HEALTH);
    }

    /**
     * Retrieves all equipment this player is wearing. Not all equipment is visible to remote players (e.g. ring slot.)
     *
     * @return All equipment this player is wearing.
     */
    public Map<EquipmentSlot, Item> getEquipment() {
        Map<EquipmentSlot, Item> conv = new HashMap<>();
        for (EquipmentSlot slot : equipment.keySet()) {
            int id = equipment.get(slot);
            conv.put(slot, new Item(id));
        }
        return conv;
    }

    /**
     * Retrieves the total level of this player. This may only be valid if their total level is displayed.
     *
     * @return The total level of this player, if accessible.
     */
    public int getTotalLevel() {
        return totalLevel;
    }

    /**
     * Retrieves the combat level of this player. This may only be valid if their combat level is displayed.
     *
     * @return The combat level of this player, if accessible.
     */
    public int getCombatLevel() {
        return combatLevel;
    }

    /**
     * Retrieves this player's health percentage (0-1), if they are currently in combat.
     *
     * @return This player's health percentage, or 0 if not in combat.
     */
    public float getHealth() {
        if (!isStatusBarActive(STATUS_HEALTH)) {
            return 0.0f;
        }

        return getStatusBarFill(STATUS_HEALTH);
    }

    /**
     * Retrieves this player's adrenaline fill percentage (0-1), if currently available.
     *
     * @return This player's adrenaline fill percentage, or 0 if not available.
     */
    public float getAdrenaline() {
        if (!isStatusBarActive(STATUS_ADRENALINE)) {
            return 0.0f;
        }

        return getStatusBarFill(STATUS_ADRENALINE);
    }

    @Override
    public String toString() {
        return "Player{" +
                "serverIndex=" + getServerIndex() +
                '}';
    }
}
