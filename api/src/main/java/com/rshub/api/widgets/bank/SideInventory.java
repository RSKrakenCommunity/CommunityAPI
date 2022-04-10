package com.rshub.api.widgets.bank;

public enum SideInventory {

    INVENTORY(0),
    EQUIPMENT(2),
    BEAST_OF_BURDEN(1);

    private final int value;

    SideInventory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
