package kraken.plugin.api;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides simplified access to the character design widgets.
 */
public final class Design {

    private static final int DESIGN_WIDGET_ID = 1420;

    private static final int DESIGN_MALE_INTERACT_ID = 93061255;
    private static final int DESIGN_FEMALE_INTERACT_ID = 93061248;
    private static final int DESIGN_RANDOMIZE_INTERACT_ID = 93061274;
    private static final int DESIGN_NAME_INTERACT_ID = 51511317;

    private static final int DESIGN_DONE_INTERACT_ID = 93061195;

    private Design() {
    }

    /**
     * Determines if the design widget is open or not.
     */
    public static boolean isOpen() {
        return Widgets.isOpen(DESIGN_WIDGET_ID);
    }


    /**
     * Selects the male character base.
     */
    public static void male() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, DESIGN_MALE_INTERACT_ID, 0);
    }

    /**
     * Selects the female character base.
     */
    public static void female() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, DESIGN_FEMALE_INTERACT_ID, 0);
    }

    /**
     * Randomizes the design appearance.
     */
    public static void randomize() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, DESIGN_RANDOMIZE_INTERACT_ID, 0);
    }

    /**
     * Selects a randomly generated name.
     */
    public static void randomName() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, 0, DESIGN_NAME_INTERACT_ID + ThreadLocalRandom.current().nextInt() % 3, 0);
    }

    /**
     * Click the done button on our design.
     */
    public static void done() {
        Actions.menu(Actions.MENU_EXECUTE_WIDGET, 1, -1, DESIGN_DONE_INTERACT_ID, 0);
    }
}
