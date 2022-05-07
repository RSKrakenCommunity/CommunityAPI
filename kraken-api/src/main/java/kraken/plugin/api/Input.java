package kraken.plugin.api;

/**
 * Provides access to real game input.
 */
public final class Input {

    public static final int MOUSE_LEFT = 0;
    public static final int MOUSE_RIGHT = 1;
    public static final int MOUSE_MIDDLE = 2;

    private Input() {
    }

    /**
     * Presses a key on the keyboard.
     *
     * @param vk The virtual key code of the key to press.
     */
    public static native void key(int vk);

    /**
     * Moves the mouse.
     *
     * @param x The x coordinate to move the mouse to.
     * @param y The y coordinate to move the mouse to.
     */
    public static native void moveMouse(int x, int y);

    /**
     * Clicks the mouse at the last known mouse coordinates.
     *
     * @param button The mouse button to click.
     */
    public static native void clickMouse(int button);

}
