package com.rshub.imgui.nodes.inputs.text

import com.rshub.imgui.nodes.Labeled
import com.rshub.imgui.skins.Skin
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

open class TextField(text: String, maxChars: Int = 50) : Labeled {
    override val labelProperty: SimpleStringProperty = SimpleStringProperty(this, "label", text)

    val maxLength = SimpleIntegerProperty(this, "max_length", maxChars)
    val input = SimpleStringProperty(this, "input", "")

    override fun getSkin(): Skin<*> {
        return TextFieldSkin(this)
    }
}