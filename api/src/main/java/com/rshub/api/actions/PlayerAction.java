package com.rshub.api.actions;

public enum PlayerAction implements ActionType {
    PLAYER1(1),
    PLAYER2(2),
    PLAYER3(3),
    PLAYER4(4),
    PLAYER5(26),
    PLAYER6(27),
    PLAYER7(29),
    PLAYER8(30);

    private final int type;

    PlayerAction(int type) {
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
