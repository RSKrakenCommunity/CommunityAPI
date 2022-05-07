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
    private int parentId = -1;
    private int childId = -1;

    private Widget() {
    }

    public int getInteractId() {
        return (this.parentId & '\uffff') << 16 | this.childId & '\uffff';
    }

    public int getType() {
        return this.type;
    }

    public Widget[] getChildren() {
        return this.children;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public Widget getChild(int index) {
        if (this.type != 0) {
            return null;
        } else {
            return index >= 0 && index < this.children.length ? this.children[index] : null;
        }
    }

    public Widget getChild(Filter<Widget> filter, int... indices) {
        Widget w = this;

        for(int i = 0; i < indices.length && w != null && filter.accept(w); ++i) {
            w = this.getChild(indices[i]);
        }

        return w;
    }

    public Widget getChild(int... indices) {
        Widget w = this;

        for(int i = 0; i < indices.length && w != null; ++i) {
            w = this.getChild(indices[i]);
        }

        return w;
    }

    public byte[] getTextBinary() {
        return this.textBinary;
    }

    public String getText() {
        byte[] bin = this.getTextBinary();
        return bin == null ? null : new String(Text.filterSpecialChars(bin), StandardCharsets.US_ASCII);
    }

    public Item getItem() {
        return this.item;
    }

    public Vector2i getPosition() {
        return this.position;
    }

    public Vector2i getSize() {
        return this.size;
    }

    public int getTextureIdDisabled() {
        return this.textureIdDisabled;
    }

    public int getTextureIdEnabled() {
        return this.textureIdEnabled;
    }

    public int getParentId() {
        return this.parentId;
    }

    public int getChildId() {
        return this.childId;
    }

    public void interact(int option) {
        Actions.menu(14, 1, option, this.getInteractId(), 0);
    }

    public void interact() {
        this.interact(-1);
    }

    public String toString() {
        return "Widget{type= " + this.getType() + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Widget widget = (Widget)o;
            return this.internal1 == widget.internal1;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.internal1});
    }
}
