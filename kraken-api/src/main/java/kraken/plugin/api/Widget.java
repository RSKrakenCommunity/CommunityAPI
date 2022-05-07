package kraken.plugin.api;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static kraken.plugin.api.Text.filterSpecialChars;

/**
 * A widget.
 */
public class Widget {

    public static final int INVALID = -1;
    public static final int CONTAINER = 0;
    public static final int TEXT = 4;
    public static final int MEDIA = 5;

    // internal values, attempting to use these will break the client

    private long internal1;

    private int type = -1;
    private Widget[] children;
    private boolean visible = false;
    private byte[] textBinary;
    private Item item = new Item(-1, 0);
    private Vector2i position;
    private Vector2i size;
    private int textureIdDisabled = -1;
    private int textureIdEnabled = -1;

    /**
     * Do not make instances of this.
     */
    private Widget() {
    }

    /**
     * Retrieves the type of this widget.
     *
     * @return The type of this widget.
     */
    public int getType() {
        return type;
    }

    /**
     * Retrieves the children in this widget.
     *
     * @return The children in this widget.
     */
    public Widget[] getChildren() {
        return children;
    }

    /**
     * Determines if this widget has been rendered onto the screen recently.
     *
     * @return If this widget has been rendered onto the screen recently.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Retrieves a child by index. Will return NULL if index is out of bounds.
     */
    public Widget getChild(int index) {
        if (type != Widget.CONTAINER) {
            return null;
        }

        if (index < 0 || index >= children.length) {
            return null;
        }

        return children[index];
    }

    /**
     * Retrieves a child by index. Will return NULL if index is out of bounds.
     */
    public Widget getChild(Filter<Widget> filter, int... indices) {
        Widget w = this;
        for (int i = 0; i < indices.length && w != null && filter.accept(w); i++) {
            w = getChild(indices[i]);
        }
        return w;
    }

    /**
     * Retrieves a child by index. Will return NULL if index is out of bounds.
     */
    public Widget getChild(int... indices) {
        Widget w = this;
        for (int i = 0; i < indices.length && w != null; i++) {
            w = getChild(indices[i]);
        }
        return w;
    }

    /**
     * Retrieves the text being stored in this widget.
     *
     * @return The text being stored in this widget, or NULL if the widget has no text.
     */
    public byte[] getTextBinary() {
        return textBinary;
    }

    /**
     * Retrieves the text being stored in this widget.
     *
     * @return The text being stored in this widget, or NULL if the widget has no text.
     */
    public String getText() {
        byte[] bin = getTextBinary();
        if (bin == null) {
            return null;
        }

        return new String(filterSpecialChars(bin), StandardCharsets.US_ASCII);
    }

    /**
     * Retrieves the item being stored in this widget.
     *
     * @return The item being stored in this widget, or NULL if the widget has no item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Retrieves the position of this widget on the screen. May not be valid for all widgets.
     *
     * @return The position of this widget.
     */
    public Vector2i getPosition() {
        return position;
    }

    /**
     * Retrieves the size of this widget on the screen. May not be valid for all widgets.
     *
     * @return The size of this widget.
     */
    public Vector2i getSize() {
        return size;
    }

    /**
     * Retrieves the texture id this widget displays when the widget is in a disabled state.
     *
     * @return The texture id this widget displays when the widget is in a disabled state.
     */
    public int getTextureIdDisabled() {
        return textureIdDisabled;
    }

    /**
     * Retrieves the texture id this widget displays when the widget is in an enabled state.
     *
     * @return The texture id this widget displays when the widget is in an enabled state.
     */
    public int getTextureIdEnabled() {
        return textureIdEnabled;
    }

    @Override
    public String toString() {
        return "Widget{" +
                "type= " + getType() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Widget widget = (Widget) o;
        return internal1 == widget.internal1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(internal1);
    }
}
