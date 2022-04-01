package com.rshub.api.actions;

public enum NpcAction implements ActionType {

    NPC1(5),
    NPC2(6),
    NPC3(7),
    NPC4(8),
    NPC5(9),
    NPC6(10);

    private final int type;

    NpcAction(int type) {
        this.type = type;
    }

    @Override
    public int getActionIndex() {
        return ordinal();
    }

    @Override
    public int getType() {
        return type;
    }

    public static NpcAction forAction(int index) {
        for (NpcAction value : values()) {
            if(value.getActionIndex() == index) {
                return value;
            }
        }
        return NpcAction.NPC1;
    }
}
