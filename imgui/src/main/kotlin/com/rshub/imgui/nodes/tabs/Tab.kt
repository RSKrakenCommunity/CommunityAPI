package com.rshub.imgui.nodes.tabs

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.nodes.Node
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty

class Tab(title: String) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", title)

    val contentProperty = SimpleObjectProperty<Node>(this, "content")
    var content: Node
        get() = contentProperty.get()
        set(value) {
            contentProperty.set(value)
        }

    override fun getSkin(): Skin<*> {
        TODO("Not yet implemented")
    }
}