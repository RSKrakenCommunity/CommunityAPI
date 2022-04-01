package com.rshub.api.skills;

import kotlinx.serialization.Serializable;
import kraken.plugin.api.Client;
import kraken.plugin.api.Stat;

public enum Skill {

    ATTACK(0),
    DEFENSE(1),
    STRENGTH(2),
    CONSTITUTION(3),
    RANGE(4),
    PRAYER(5),
    MAGIC(6),
    COOKING(7),
    WOODCUTTING(8),
    FLETCHING(9),
    FISHING(10),
    FIREMAKING(11),
    CRAFTING(12),
    SMITHING(13),
    MINING(14),
    HERBLORE(15),
    AGILITY(16),
    THIEVING(17),
    SLAYER(18),
    FARMING(19),
    RUNECRAFTING(20),
    HUNTER(21),
    CONSTRUCTION(22),
    SUMMONING(23),
    DUNGEONEERING(24),
    DIVINATION(25),
    INVENTION(26),
    ARCHAEOLOGY(27),
    NONE(-1)
    ;

    private final int id;

    Skill(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        Stat stat = Client.getStatById(id);
        if(stat == null) return 0;
        return stat.getCurrent();
    }
}
