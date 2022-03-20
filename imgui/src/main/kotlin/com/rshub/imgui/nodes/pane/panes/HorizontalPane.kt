package com.rshub.imgui.nodes.pane.panes

import com.rshub.imgui.nodes.Node
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections

class HorizontalPane : Node {
    internal val nodes = SimpleListProperty<Node>(this, "nodes", FXCollections.observableArrayList())

    fun add(node: Node) {
        nodes.add(node)
    }

    fun remove(node: Node) {
        nodes.remove(node)
    }

    override fun getSkin(): Skin<*> {
        TODO("Not yet implemented")
    }
}