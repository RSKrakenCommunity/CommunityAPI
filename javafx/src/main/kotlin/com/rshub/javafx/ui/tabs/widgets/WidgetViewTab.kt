package com.rshub.javafx.ui.tabs.widgets

import com.rshub.definitions.maps.WorldTile.Companion.tile
import com.rshub.javafx.ui.model.WidgetChildModel
import com.rshub.javafx.ui.model.WidgetModel
import javafx.scene.control.TreeItem
import kraken.plugin.api.Client
import kraken.plugin.api.Widget
import kraken.plugin.api.Widgets
import tornadofx.*

class WidgetViewTab : Fragment("Widgets") {

    private val model: WidgetModel by di()

    override val root = hbox {
        treeview<WidgetChildModel> {
            rootProperty().bind(model.root)
            cellFormat {
                text = if(item.widget.get() == null) {
                    "Root"
                } else {
                    val typeName = when(item.widget.get().type) {
                        4 -> "Text"
                        else -> "${item.widget.get().type}"
                    }
                    "${item.id.get()} - $typeName - ${item.widget.get().text}"
                }
                Widgets.getGroupById(1466)
                    .getWidget(0)
                    .getChild(0)
                    .getChild(2, 14).position


                val text = item.widget.get()?.position?.toString()
                if (text != null) {
                    tooltip = tooltip(text)
                }
            }
        }
        form {
            fieldset {
                field("Group ID") {
                    textfield(model.groupId) {
                        stripNonInteger()
                    }
                }
                field {
                    button("View Widget") {
                        disableWhen(model.groupId.isEqualTo(-1))
                        setOnAction {
                            val group = Widgets.getGroupById(model.groupId.get())
                            val item = TreeItem(WidgetChildModel(0, null))
                            for (widget in group.widgets) {
                                addChildren(item, widget)
                            }
                            model.root.set(item)
                        }
                    }
                }
            }
        }
    }

    private fun addChildren(parent: TreeItem<WidgetChildModel>, widget: Widget) {
        if (widget.children != null) {
            for ((index, child) in widget.children.withIndex()) {
                if (child != null) {
                    val p = TreeItem(WidgetChildModel(index, child))
                    addChildren(p, child)
                    parent.children.add(p)
                }
            }
        }
    }
}