package com.rshub.imgui.nodes.inputs.integer

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

class IntegerInput(text: String) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", text)

    val input = SimpleIntegerProperty(this, "input", 0)

    override fun getSkin(): Skin<*> {
        return IntegerInputSkin(this)
    }
}