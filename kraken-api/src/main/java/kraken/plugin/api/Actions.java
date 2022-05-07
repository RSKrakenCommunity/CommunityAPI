package kraken.plugin.api;

/**
 * A provider of actions.
 */
public final class Actions {

    // TODO FIXME re-order all of these one day
    public static final int MENU_EXECUTE_WALK = 0;
    public static final int MENU_EXECUTE_PLAYER1 = 1;
    public static final int MENU_EXECUTE_PLAYER2 = 2;
    public static final int MENU_EXECUTE_PLAYER3 = 3;
    public static final int MENU_EXECUTE_PLAYER4 = 4;
    public static final int MENU_EXECUTE_NPC1 = 5;
    public static final int MENU_EXECUTE_NPC2 = 6;
    public static final int MENU_EXECUTE_NPC3 = 7;
    public static final int MENU_EXECUTE_NPC4 = 8;
    public static final int MENU_EXECUTE_NPC5 = 9;
    public static final int MENU_EXECUTE_NPC6 = 10;
    public static final int MENU_EXECUTE_WIDGET_ITEM = 11;
    public static final int MENU_EXECUTE_OBJECT2 = 12;
    public static final int MENU_EXECUTE_OBJECT1 = 13;
    public static final int MENU_EXECUTE_WIDGET = 14;
    public static final int MENU_EXECUTE_GROUND_ITEM3 = 15;
    public static final int MENU_EXECUTE_DIALOGUE = 16;
    public static final int MENU_EXECUTE_SELECTABLE_WIDGET = 17;
    public static final int MENU_EXECUTE_SELECT_WIDGET_ITEM = 18;
    public static final int MENU_EXECUTE_OBJECT3 = 19;
    public static final int MENU_EXECUTE_OBJECT4 = 20;
    public static final int MENU_EXECUTE_OBJECT5 = 21;
    public static final int MENU_EXECUTE_OBJECT6 = 22;
    public static final int MENU_EXECUTE_SELECT_GROUND_ITEM = 23;
    public static final int MENU_EXECUTE_SELECT_NPC = 24;
    public static final int MENU_EXECUTE_SELECT_OBJECT = 25;
    public static final int MENU_EXECUTE_PLAYER5 = 26;
    public static final int MENU_EXECUTE_PLAYER6 = 27;
    public static final int MENU_EXECUTE_SELECT_PLAYER = 28;
    public static final int MENU_EXECUTE_PLAYER7 = 29;
    public static final int MENU_EXECUTE_PLAYER8 = 30;
    public static final int MENU_EXECUTE_GROUND_ITEM1 = 31;
    public static final int MENU_EXECUTE_GROUND_ITEM2 = 32;
    public static final int MENU_EXECUTE_GROUND_ITEM4 = 33;
    public static final int MENU_EXECUTE_GROUND_ITEM5 = 34;
    public static final int MENU_EXECUTE_GROUND_ITEM6 = 35;

    /**
     * All actions that are valid on NPCs.
     */
    private static final int[] VALID_NPC_ACTIONS = {
            MENU_EXECUTE_NPC1,
            MENU_EXECUTE_NPC2,
            MENU_EXECUTE_NPC3,
            MENU_EXECUTE_NPC4,
            MENU_EXECUTE_NPC5,
            MENU_EXECUTE_NPC6,
            MENU_EXECUTE_SELECT_NPC,
    };

    /**
     * All actions that are valid on objects.
     */
    private static final int[] VALID_OBJECT_ACTIONS = {
            MENU_EXECUTE_OBJECT1,
            MENU_EXECUTE_OBJECT2,
            MENU_EXECUTE_OBJECT3,
            MENU_EXECUTE_OBJECT4,
            MENU_EXECUTE_OBJECT5,
            MENU_EXECUTE_OBJECT6,
            MENU_EXECUTE_SELECT_OBJECT,
    };

    /**
     * All actions that are valid on players.
     */
    private static final int[] VALID_PLAYER_ACTIONS = {
            MENU_EXECUTE_PLAYER1,
            MENU_EXECUTE_PLAYER2,
            MENU_EXECUTE_PLAYER3,
            MENU_EXECUTE_PLAYER4,
            MENU_EXECUTE_PLAYER5,
            MENU_EXECUTE_PLAYER6,
            MENU_EXECUTE_PLAYER7,
            MENU_EXECUTE_PLAYER8,
            MENU_EXECUTE_SELECT_PLAYER,
    };

    /**
     * All actions that are valid on ground items.
     */
    private static final int[] VALID_GROUND_ITEM_ACTIONS = {
            MENU_EXECUTE_GROUND_ITEM1,
            MENU_EXECUTE_GROUND_ITEM2,
            MENU_EXECUTE_GROUND_ITEM3,
            MENU_EXECUTE_GROUND_ITEM4,
            MENU_EXECUTE_GROUND_ITEM5,
            MENU_EXECUTE_GROUND_ITEM6,
            MENU_EXECUTE_SELECT_GROUND_ITEM,
    };

    private Actions() {
    }

    /**
     * Executes a synthetic menu action.
     */
    public static native void menu(int type, int param1, int param2, int param3, int param4);

    // Entity utilities.

    public static void entity(SceneObject object, int type, int xOff, int yOff) {
        if (!Array.contains(VALID_OBJECT_ACTIONS, type)) {
            throw new BadActionException("Bad object action type");
        }

        Vector3i pos = object.getGlobalPosition();
        Actions.menu(type, object.getInteractId(), pos.getX() + xOff, pos.getY() + yOff, 1);
    }

    public static void entity(SceneObject object, int type) {
        entity(object, type, 0, 0);
    }

    public static void entity(Spirit spirit, int type) {
        if (spirit instanceof Player) {
            if (!Array.contains(VALID_PLAYER_ACTIONS, type)) {
                throw new BadActionException("Bad player action type");
            }
        } else if (spirit instanceof Npc) {
            if (!Array.contains(VALID_NPC_ACTIONS, type)) {
                throw new BadActionException("Bad NPC action type");
            }
        } else {
            throw new BadActionException("Bad entity type");
        }

        Actions.menu(type, spirit.getServerIndex(), 0, 0, 1);
    }

    public static void entity(GroundItem item, int type) {
        if (!Array.contains(VALID_GROUND_ITEM_ACTIONS, type)) {
            throw new BadActionException("Bad ground item action type");
        }

        Actions.menu(type, item.getId(), item.getGlobalPosition().getX(), item.getGlobalPosition().getY(), 0);
    }
}
