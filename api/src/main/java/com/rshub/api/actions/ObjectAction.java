package com.rshub.api.actions;

import kotlinx.serialization.Serializable;

public enum ObjectAction implements ActionType {

    OBJECT1(13),
    OBJECT2(12),
    OBJECT3(19),
    OBJECT4(20),
    OBJECT5(21),
    OBJECT6(22);

    private final int type;

    ObjectAction(int type) {
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

    public static ObjectAction forAction(int index) {
        for (ObjectAction value : values()) {
            if(value.getActionIndex() == index) {
                return value;
            }
        }
        return OBJECT1;
    }
}
