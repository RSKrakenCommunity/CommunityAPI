package com.rshub.api.actions;

import kraken.plugin.api.Actions;

public class ActionHelper {
    private ActionHelper() {}

    public static void menu(MenuAction action, int param1, int param2, int param3) {
        Actions.menu(action.type, param1, param2, param3, 0);
    }
}
