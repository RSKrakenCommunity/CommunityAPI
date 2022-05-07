package kraken.plugin.api;


/**
 * Provides access to various client state.
 */
public final class Client {

    public static final int ATTACK = 0;
    public static final int DEFENSE = 1;
    public static final int STRENGTH = 2;
    public static final int HITPOINTS = 3;
    public static final int RANGE = 4;
    public static final int PRAYER = 5;
    public static final int MAGIC = 6;
    public static final int COOKING = 7;
    public static final int WOODCUTTING = 8;
    public static final int FLETCHING = 9;
    public static final int FISHING = 10;
    public static final int FIREMAKING = 11;
    public static final int CRAFTING = 12;
    public static final int SMITHING = 13;
    public static final int MINING = 14;
    public static final int HERBLORE = 15;
    public static final int AGILITY = 16;
    public static final int THIEVING = 17;
    public static final int SLAYER = 18;
    public static final int FARMING = 19;
    public static final int RUNECRAFTING = 20;
    public static final int HUNTER = 21;
    public static final int CONSTRUCTION = 22;
    public static final int SUMMONING = 23;
    public static final int DUNGEONEERING = 24;
    public static final int DIVINATION = 25;
    public static final int INVENTION = 26;
    public static final int ARCHAEOLOGY = 27;

    public static final int AT_LOGIN = 10;
    public static final int IN_LOBBY = 20;
    public static final int IN_GAME = 30;

    public static final int REPORT_EXPLOITING_BUGS = 5;
    public static final int REPORT_STAFF_IMPERSONATION = 6;
    public static final int REPORT_BUYING_OR_SELLING_ACCOUNT = 7;
    public static final int REPORT_MACROING_OR_USE_OF_BOTS = 8;
    public static final int REPORT_ENCOURAGING_RULE_BREAKING = 10;
    public static final int REPORT_ADVERTISING_WEBSITES = 12;
    public static final int REPORT_ASKING_FOR_CONTACT_INFO = 14;
    public static final int REPORT_SCAMMING = 16;
    public static final int REPORT_OFFENSIVE_LANGUAGE = 17;
    public static final int REPORT_SOLICITATION = 18;
    public static final int REPORT_DISRUPTIVE_BEHAVIOR = 19;
    public static final int REPORT_OFFENSIVE_NAME = 20;
    public static final int REPORT_REAL_LIFE_THREATS = 21;
    public static final int REPORT_BREAKING_REAL_LAWS = 22;
    public static final int REPORT_GAMES_OF_CHANCE = 23;

    private static final int WIDGET_INTERACT_LOGOUT_ID = 93913158;
    private static final int WIDGET_INTERACT_LOGOUT2_ID = 93913172;
    private static final int WIDGET_INTERACT_AUTO_RETALIATE_TOGGLE_LEGACY = 98500658;
    private static final int WIDGET_INTERACT_AUTO_RETALIATE_TOGGLE_NEW = 93716537;
    private static final int WIDGET_INTERACT_EXIT_TO_LOBBY_ID = 93913155;
    private static final int WIDGET_INTERACT_AUTO_RUN_TOGGLE = 96010251;

    private Client() {
    }

    /**
     * Retrieves the state that the client is currently in.
     *
     * @return The state that the client is currently in.
     */
    public static native int getState();

    /**
     * Determines if the client is in a loading state.
     *
     * @return If the client is in a loading state.
     */
    public static native boolean isLoading();

    /**
     * Retrieves a stat by id.
     *
     * @param id The id of the stat to retrieve.
     * @return The stat with the provided id, or NULL if one was not found.
     */
    public static native Stat getStatById(int id);

    /**
     * Retrieves a connection variable by id.
     *
     * @param id The id of the variable to search for.
     * @return The found variable with the provided id, or NULL if one was not found.
     */
    public static native ConVar getConVarById(int id);

    /**
     * Retrieves a script variable by id.
     *
     * @param id The id of the variable to search for.
     * @return The found variable with the provided id, or NULL if one was not found.
     */
    public static native ScriptVar getScrVarById(int id);

    /**
     * Retrieves the combat mode that the game is in.
     */
    public static GameTheme getCombatMode() {
        ConVar cv = getConVarById(ConVar.ID_COMBAT_MODE);
        if (cv == null) {
            return GameTheme.NEW;
        }

        return cv.getValueInt() == ConVar.CFG_COMBAT_MODE_LEGACY ? GameTheme.LEGACY : GameTheme.NEW;
    }

    /**
     * Retrieves the interface mode that the game is in.
     */
    public static GameTheme getInterfaceMode() {
        ConVar cv = getConVarById(ConVar.ID_INTERFACE_MODE);
        if (cv == null) {
            return GameTheme.NEW;
        }

        return cv.getValueInt() == ConVar.CFG_INTERFACE_MODE_LEGACY ? GameTheme.LEGACY : GameTheme.NEW;
    }

    /**
     * Determines if the local player's weapon is sheathed or not.
     */
    public static boolean isWeaponSheathed() {
        ConVar cv = getConVarById(ConVar.ID_WEAPON_SHEATHE);
        if (cv == null) {
            return false;
        }

        return cv.getValueInt() != ConVar.CFG_WEAPON_SHEATHE_ACTIVE;
    }

    /**
     * Determines if auto retaliate is enabled.
     */
    public static boolean isAutoRetaliating() {
        ConVar cv = getConVarById(ConVar.ID_AUTO_RETALIATE);
        if (cv == null) {
            return true;
        }

        return cv.getValueInt() == ConVar.CFG_AUTO_RETALIATE_ON;
    }

    /**
     * Changes whether or not we're auto retaliating when attacked.
     */
    public static void setAutoRetaliating(boolean auto) {
        if (isAutoRetaliating() != auto) {
            int id;
            if (getInterfaceMode() == GameTheme.LEGACY) {
                id = WIDGET_INTERACT_AUTO_RETALIATE_TOGGLE_LEGACY;
            } else {
                id = WIDGET_INTERACT_AUTO_RETALIATE_TOGGLE_NEW;
            }

            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, id, 0);
        }
    }

    /**
     * Determines if running is enabled.
     */
    public static boolean isRunning() {
        ConVar cv = getConVarById(ConVar.ID_RUNNING);
        if (cv == null) {
            return false;
        }

        return cv.getValueInt() == ConVar.CFG_RUNNING_ON;
    }

    /**
     * Changes whether or not we're running instead of walking.
     */
    public static void setRunning(boolean running) {
        if (isRunning() != running) {
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_INTERACT_AUTO_RUN_TOGGLE, 0);
        }
    }

    /**
     * Retrieves the current health of the local player.
     *
     * @return The current health of the local player.
     */
    public static int getCurrentHealth() {
        ConVar cv = getConVarById(ConVar.ID_LOCAL_STATUS);
        if (cv == null) {
            return 0;
        }

        return (cv.getId() & 0xffff) / 2;
    }

    /**
     * Retrieves the maximum health of the local player.
     *
     * @return The maximum health of the local player.
     */
    public static int getMaxHealth() {
        ConVar cv = getConVarById(ConVar.ID_LOCAL_STATUS);
        if (cv == null) {
            return 0;
        }

        return (cv.getId() >> 16) & 0xffff;
    }

    /**
     * Retrieves the privacy level of the local player.
     *
     * @return The privacy level of the local player.
     */
    public static PrivacyLevel getPrivacyLevel() {
        ConVar cv = getConVarById(ConVar.ID_PRIVACY);
        if (cv == null) {
            return PrivacyLevel.ANYBODY;
        }

        switch (cv.getValueInt()) {
            case ConVar.CFG_PRIVACY_FRIENDS_ONLY:
                return PrivacyLevel.FRIENDS_ONLY;
            case ConVar.CFG_PRIVACY_NOBODY:
                return PrivacyLevel.NOBODY;
            default:
                return PrivacyLevel.ANYBODY;
        }
    }

    /**
     * Changes the privacy level of the local player.
     *
     * @param level The new privacy level.
     */
    public static void changePrivacyLevel(PrivacyLevel level) {
        if (getPrivacyLevel() == level) {
            return;
        }

        int[] indices = new int[] { 1, 3, 5 };
        if (!Widgets.isOpen(1446)) {
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, 0, 93913094, 0);
        } else {
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, 3, 96797338, 0);
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, 94765142, 0);
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, 102301740, 0);
            Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, indices[level.ordinal()], 96797518, 0);
        }
    }

    /**
     * Retrieves the current prayer of the local player.
     *
     * @return The current prayer of the local player.
     */
    public static native int getCurrentPrayer();

    /**
     * Retrieves the maximum prayer of the local player.
     *
     * @return The maximum prayer of the local player.
     */
    public static native int getMaxPrayer();

    /**
     * Retrieves the current run energy of the local player.
     *
     * @return The current run energy of the local player.
     */
    public static native int getRunEnergy();

    /**
     * Makes a report against a player.
     */
    public static native void report(int type, String player);

    /**
     * Projects a world point to the screen.
     *
     * @param vec The world point to project.
     * @return The projected point, or NULL if projection failed.
     */
    public static native Vector2i worldToScreen(Vector3 vec);

    /**
     * Projects a world point to the minimap.
     *
     * @param vec The world point to project.
     * @return The projected poinr, or NULL if projection failed.
     */
    public static native Vector2i worldToMinimap(Vector3 vec);

    /**
     * Projects multiple world points to the screen.
     * <p>
     * Good for projecting tile points, model points, etc.
     *
     * @param in The input vectors to project.
     * @return If all points were successfully projected.
     */
    public static boolean multiWorldToScreen(Vector3[] in, Vector2i[] out) {
        if (in.length != out.length) {
            return false;
        }

        for (int i = 0; i < in.length; i++) {
            Vector3 v = in[i];
            if (v == null) {
                return false;
            }

            Vector2i projected = worldToScreen(v);
            if (projected == null) {
                return false;
            }

            out[i] = projected;
        }

        return true;
    }

    /**
     * Logs out by clicking the logout button.
     */
    public static void logout() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_INTERACT_LOGOUT_ID, 0);
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_INTERACT_LOGOUT2_ID, 0);
    }

    /**
     * Exits to the game lobby.
     */
    public static void exitToLobby() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, WIDGET_INTERACT_EXIT_TO_LOBBY_ID, 0);
    }

    /**
     * Disconnects from the game server forcefully by closing the networking socket.
     */
    public static native void disconnect();

}
