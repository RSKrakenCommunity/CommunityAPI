package kraken.plugin.api;

import java.util.Objects;

/**
 * A group of widgets.
 */
public class WidgetGroup {

    // internal values, attempting to use these will break the client

    private long internal1;

    private int id;
    private Widget[] widgets;

    /**
     * Do not make instances of this.
     */
    private WidgetGroup() {
    }

    /**
     * Retrieves the id of this group.
     *
     * @return The id of this group.
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves a widget by index. Will return NULL if index is out of bounds.
     */
    public Widget getWidget(int index) {
        // nobody actually wants to check for this or handle this..
        if (index < 0 || index >= widgets.length) {
            return null;
        }

        return widgets[index];
    }

    /**
     * Retrieves all widgets in this group.
     *
     * @return All widgets in this group.
     */
    public Widget[] getWidgets() {
        return widgets;
    }

    @Override
    public String toString() {
        return "WidgetGroup{" +
                "id=" + getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WidgetGroup that = (WidgetGroup) o;
        return internal1 == that.internal1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal1);
    }
}
