package com.rshub.imgui.nodes.pane.panes

import com.rshub.imgui.nodes.Node
import com.rshub.imgui.nodes.pane.skins.VerticalPaneSkin
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections

class VerticalPane : Node {
    internal val nodes = SimpleListProperty<Node>(this, "nodes", FXCollections.observableArrayList())

    fun add(node: Node) {
        nodes.add(node)
    }

    fun remove(node: Node) {
        nodes.remove(node)
    }

    override fun getSkin(): Skin<VerticalPane> {
        return VerticalPaneSkin(this)
    }
}