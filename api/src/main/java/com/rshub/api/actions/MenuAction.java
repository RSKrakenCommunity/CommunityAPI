package com.rshub.api.actions;

public enum MenuAction implements ActionType {

    WALK(0),
    PLAYER1(1),
    PLAYER2(2),
    PLAYER3(3),
    PLAYER4(4),
    PLAYER5(26),
    PLAYER6(27),
    PLAYER7(29),
    PLAYER8(30),
    NPC1(5),
    NPC2(6),
    NPC3(7),
    NPC4(8),
    NPC5(9),
    NPC6(10),

    OBJECT1(13),
    OBJECT2(12),
    OBJECT3(19),
    OBJECT4(20),
    OBJECT5(21),
    OBJECT6(22),

    GROUND_ITEM1(31),
    GROUND_ITEM2(32),
    GROUND_ITEM3(15),
    GROUND_ITEM4(33),
    GROUND_ITEM5(34),
    GROUND_ITEM6(35),

    WIDGET_ITEM(11),
    WIDGET(14),
    SELECTABLE_WIDGET(17),
    SELECT_WIDGET_ITEM(18),
    SELECT_GROUND_ITEM(23),
    SELECT_NPC(24),
    SELECT_OBJECT(25),
    SELECT_PLAYER(28)
    ;

    private final int type;

    MenuAction(int type) {
        this.type = type;
    }

    @Override
    public int getActionIndex() {
        return -1;
    }

    @Override
    public int getType() {
        return type;
    }
}
