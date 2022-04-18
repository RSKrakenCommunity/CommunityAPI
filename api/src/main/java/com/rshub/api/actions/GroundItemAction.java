package com.rshub.api.actions;

public enum GroundItemAction implements ActionType {
    GROUND_ITEM1(31),
    GROUND_ITEM2(32),
    GROUND_ITEM3(15),
    GROUND_ITEM4(33),
    GROUND_ITEM5(34),
    GROUND_ITEM6(35);

    private final int type;

    GroundItemAction(int type) {
        this.type = type;
    }

    @Override
    public int getActionIndex() {
        return ordinal();
    }

    @Override
    public int getType() {
        return this.type;
    }
}
