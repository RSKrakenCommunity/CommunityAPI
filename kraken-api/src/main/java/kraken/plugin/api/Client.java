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

    public static native int getWorld();

    public static native int getState();

    public static native boolean isLoading();

    public static native Stat getStatById(int var0);

    public static native ConVar getConVarById(int var0);

    public static native ScriptVar getScrVarById(int var0);

    public static GameTheme getCombatMode() {
        ConVar cv = getConVarById(3711);
        if (cv == null) {
            return GameTheme.NEW;
        } else {
            return cv.getValueInt() == 11328 ? GameTheme.LEGACY : GameTheme.NEW;
        }
    }

    public static GameTheme getInterfaceMode() {
        ConVar cv = getConVarById(3814);
        if (cv == null) {
            return GameTheme.NEW;
        } else {
            return cv.getValueInt() == 58 ? GameTheme.LEGACY : GameTheme.NEW;
        }
    }

    public static boolean isWeaponSheathed() {
        ConVar cv = getConVarById(689);
        if (cv == null) {
            return false;
        } else {
            return cv.getValueInt() != 0;
        }
    }

    public static boolean isAutoRetaliating() {
        ConVar cv = getConVarById(462);
        if (cv == null) {
            return true;
        } else {
            return cv.getValueInt() == 0;
        }
    }

    public static void setAutoRetaliating(boolean auto) {
        if (isAutoRetaliating() != auto) {
            int id;
            if (getInterfaceMode() == GameTheme.LEGACY) {
                id = 98500658;
            } else {
                id = 93716537;
            }

            Actions.menu(14, 1, -1, id, 0);
        }

    }

    public static boolean isRunning() {
        ConVar cv = getConVarById(463);
        if (cv == null) {
            return false;
        } else {
            return cv.getValueInt() == 1;
        }
    }

    public static void setRunning(boolean running) {
        if (isRunning() != running) {
            Actions.menu(14, 1, -1, 96010251, 0);
        }

    }

    public static int getCurrentHealth() {
        ConVar cv = getConVarById(659);
        return cv == null ? 0 : (cv.getValueInt() & '\uffff') / 2;
    }

    public static int getMaxHealth() {
        ConVar cv = getConVarById(659);
        return cv == null ? 0 : cv.getValueInt() >> 16 & '\uffff';
    }

    public static PrivacyLevel getPrivacyLevel() {
        ConVar cv = getConVarById(4983);
        if (cv == null) {
            return PrivacyLevel.ANYBODY;
        } else {
            switch(cv.getValueInt()) {
                case 67108864:
                    return PrivacyLevel.FRIENDS_ONLY;
                case 134217728:
                    return PrivacyLevel.NOBODY;
                default:
                    return PrivacyLevel.ANYBODY;
            }
        }
    }

    public static void changePrivacyLevel(PrivacyLevel level) {
        if (getPrivacyLevel() != level) {
            int[] indices = new int[]{1, 3, 5};
            if (!Widgets.isOpen(1446)) {
                Actions.menu(14, 1, 0, 93913094, 0);
            } else {
                Actions.menu(14, 1, 3, 96797338, 0);
                Actions.menu(14, 1, -1, 94765142, 0);
                Actions.menu(14, 1, -1, 102301740, 0);
                Actions.menu(14, 1, indices[level.ordinal()], 96797518, 0);
            }

        }
    }

    public static native int getCurrentPrayer();

    public static native int getMaxPrayer();

    public static native int getRunEnergy();

    public static native void report(int var0, String var1);

    public static native Vector2i worldToScreen(Vector3 var0);

    public static native Vector2i worldToMinimap(Vector3 var0);

    public static boolean multiWorldToScreen(Vector3[] in, Vector2i[] out) {
        if (in.length != out.length) {
            return false;
        } else {
            for(int i = 0; i < in.length; ++i) {
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
    }

    public static void logout() {
        Actions.menu(14, 1, -1, 93913158, 0);
        Actions.menu(14, 1, -1, 93913172, 0);
    }

    public static void exitToLobby() {
        Actions.menu(14, 1, -1, 93913155, 0);
    }

    public static native void disconnect();

}
