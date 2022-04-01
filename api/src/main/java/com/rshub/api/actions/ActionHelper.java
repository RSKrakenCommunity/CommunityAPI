package com.rshub.api.actions;

import kraken.plugin.api.Actions;

public final class ActionHelper {
    private ActionHelper() {}

    public static void menu(ActionType action, int param1, int param2, int param3) {
        Actions.menu(action.getType(), param1, param2, param3, 0);
    }
}
