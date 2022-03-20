package com.rshub.imgui.nodes.checkbox

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty

class Checkbox(text: String) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", text)

    val input = SimpleBooleanProperty(this, "input", false)

    override fun getSkin(): Skin<*> {
        return CheckboxSkin(this)
    }
}