package kraken.plugin.api;

/**
 * A provider of widgets.
 */
public final class Widgets {

    private Widgets() {
    }

    /**
     * Retrieves a widget group by id.
     *
     * @param id The id of the widget to retrieve.
     * @return The group with the provided id, or NULL if one was not found.
     */
    public static native WidgetGroup getGroupById(int id);

    /**
     * Determines if a widget is open.
     *
     * @param id The id of the widget to check the visibility of.
     * @return If the widget with the provided is is open.
     */
    public static boolean isOpen(int id) {
        WidgetGroup group = getGroupById(id);
        if (group == null) {
            return false;
        }

        for (Widget w : group.getWidgets()) {
            if (w.isVisible()) {
                return true;
            }
        }

        return false;
    }
}
